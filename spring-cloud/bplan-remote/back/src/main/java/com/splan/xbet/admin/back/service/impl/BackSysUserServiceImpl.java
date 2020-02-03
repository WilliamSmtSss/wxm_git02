package com.splan.xbet.admin.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.splan.base.bean.SysPermission;
import com.splan.base.bean.SysRole;
import com.splan.base.bean.SysRolePermission;
import com.splan.base.bean.SysUser;
import com.splan.base.http.CommonResult;
import com.splan.xbet.admin.back.constants.StormContants;
import com.splan.base.enums.ResultStatus;
import com.splan.xbet.admin.back.mappers.SysPermissionMapper;
import com.splan.xbet.admin.back.mappers.SysRolePermissionMapper;
import com.splan.xbet.admin.back.service.*;
import com.splan.xbet.admin.back.utils.CommonUtil;
import com.splan.xbet.admin.back.utils.ResultUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BackSysUserServiceImpl implements BackSysUserService {

    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private ISysPermissionService sysPermissionService;

    @Autowired
    private ISysRolePermissionService sysRolePermissionService;

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public CommonResult<List<SysRole>> roles() {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("delete_status", 1);
        queryWrapper.eq("api_id","");
        queryWrapper.ne("id",1);
        return ResultUtil.returnSuccess(sysRoleService.list(queryWrapper));
    }

    @Override
    public CommonResult<List<Integer>> permissionsByRoleId(Integer roleId) {
        List<Integer> result=new ArrayList<>();
        if(1==roleId)result=sysPermissionMapper.getSuperIds(StormContants.STORM_SYS_PERMISSION_MIN);
        else result=sysPermissionMapper.getNormalIds(StormContants.STORM_SYS_PERMISSION_MIN,roleId);
        return ResultUtil.returnSuccess(result);
    }

    @Override
    public CommonResult allPermissions() {
       return ResultUtil.returnSuccess(sysPermissionMapper.getAllPermissions2(StormContants.STORM_SYS_PERMISSION_MIN));
//        Set<String> set=sysPermissionMapper.getStormMeunName(StormContants.STORM_SYS_PERMISSION_MIN);
//        List<SysPermission> list=sysPermissionMapper.getAllPermissions2(StormContants.STORM_SYS_PERMISSION_MIN);
//        Map<String,List<SysPermission>> map=new HashMap<>();
//        for(String meunName:set){
//            map.put(meunName,new ArrayList<>());
//        }
//        for(SysPermission sysPermission:list){
//            map.get(sysPermission.getMenuName()).add(sysPermission);
//        }
//        List<JurisdictionResult> result=new ArrayList<>();
//        for(String key:map.keySet()){
//            JurisdictionResult jurisdictionResult=new JurisdictionResult();
//            jurisdictionResult.setMenuName(key);
//            jurisdictionResult.setSysPermissions(map.get(key));
//            result.add(jurisdictionResult);
//        }
//        return ResultUtil.returnSuccess(result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult editPermissions(String roleid,String permissionids) {
        List<String> permissionlist= Arrays.asList(permissionids.split("[,|，]"));
        if(!CommonUtil.checklist(permissionlist))
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        if(!checkPermissions(permissionlist))
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);

        //       清除该角色所有权限
        sysRolePermissionMapper.deletebyroleid(roleid);
        //       插入sys_role_permission
        for(String permissionid:permissionlist){
            SysRolePermission sysRolePermission=new SysRolePermission();
            sysRolePermission.setRoleId(Integer.parseInt(roleid));
            sysRolePermission.setDeleteStatus("1");
            sysRolePermission.setPermissionId(Integer.parseInt(permissionid));
            sysRolePermissionMapper.insert(sysRolePermission);
        }
        return ResultUtil.returnSuccess(ResultStatus.SUCCESS);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult addRole(SysRole sysRole,String permissionids) {
        if(sysRole.getId()!=null)
           return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        if(StringUtils.isBlank(sysRole.getRoleName()))
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        List<String> permissionlist= Arrays.asList(permissionids.split("[,|，]"));
        if(!CommonUtil.checklist(permissionlist))
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        if(!checkPermissions(permissionlist))
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);

        sysRole.setDeleteStatus("1");
        sysRoleService.save(sysRole);
        for(String permissionid:permissionlist){
            SysRolePermission sysRolePermission=new SysRolePermission();
            sysRolePermission.setRoleId(sysRole.getId());
            sysRolePermission.setDeleteStatus("1");
            sysRolePermission.setPermissionId(Integer.parseInt(permissionid));
            sysRolePermissionMapper.insert(sysRolePermission);
        }
        return ResultUtil.returnSuccess(ResultStatus.SUCCESS);
    }

    @Override
    public CommonResult editRole(SysRole sysRole) {
        if (sysRole.getId()==null)
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        return ResultUtil.returnSuccess(sysRoleService.updateById(sysRole));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult delRole(String roleId) {
        if(StringUtils.isBlank(roleId))
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        //超级管理员不允许删除
        if("1".equals(roleId))
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        //删除角色
        sysRoleService.removeById(roleId);
        //删除角色权限
        sysRolePermissionMapper.deleteById(roleId);
        //将账号状态设置为不可用
        UpdateWrapper<SysUser> updateWrapper=new UpdateWrapper<>();
        updateWrapper.set("delete_status",2);
        updateWrapper.eq("role_id",roleId);
        sysUserService.update(updateWrapper);
        return ResultUtil.returnSuccess(ResultStatus.SUCCESS);
    }

    @Override
    public CommonResult addPermission(SysPermission sysPermission) {
        if(sysPermission.getId()!=null) {
//            if(sysPermissionMapper.selectById(sysPermission.getId())!=null)return ResultUtil.returnError(ResultStatus.PERMISSION_EXISTS);
//            if(sysPermission.getId()<Integer.parseInt(StormContants.STORM_SYS_PERMISSION_MIN)||sysPermission.getId()>Integer.parseInt(StormContants.STORM_SYS_PERMISSION_MAX))return ResultUtil.returnError(ResultStatus.PERMISSION_IDOUT);
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        }

        if(StringUtils.isBlank(sysPermission.getMenuCode()))
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        if(StringUtils.isBlank(sysPermission.getMenuName()))
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        if(StringUtils.isBlank(sysPermission.getPermissionCode()))
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        if(StringUtils.isBlank(sysPermission.getPermissionName()))
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);

        QueryWrapper<SysPermission> queryWrapper=new QueryWrapper<>();
        queryWrapper.select("max(id) as max");
        queryWrapper.ge("id",StormContants.STORM_SYS_PERMISSION_MIN);
        queryWrapper.le("id",StormContants.STORM_SYS_PERMISSION_MAX);
        Integer MaxStormPermissionID=(Integer) sysPermissionMapper.selectMaps(queryWrapper).get(0).get("max");
        sysPermission.setId(++MaxStormPermissionID);

       return ResultUtil.returnSuccess(sysPermissionService.save(sysPermission));

    }

    @Override
    public CommonResult editPermission(SysPermission sysPermission) {
        if(sysPermission.getId()==null)
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        return ResultUtil.returnSuccess(sysPermissionService.updateById(sysPermission));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult delPermission(String permissionId) {
        if(StringUtils.isBlank(permissionId))
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        //删除权限
        sysPermissionService.removeById(permissionId);
        //删除角色权限表
        UpdateWrapper<SysRolePermission> updateWrapper=new UpdateWrapper();
        updateWrapper.eq("permission_id",permissionId);
        int result= sysRolePermissionMapper.delete(updateWrapper);
        return ResultUtil.returnSuccess(result);
    }

    private  boolean checkPermissions(List<String> permissions){
        List<String> Ids=sysPermissionMapper.getIds();
        if(!Ids.containsAll(permissions))return false;
        return true;
    }

}
