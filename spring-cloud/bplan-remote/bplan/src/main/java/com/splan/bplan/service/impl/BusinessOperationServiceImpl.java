package com.splan.bplan.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.splan.base.bean.SysPermission;
import com.splan.base.bean.SysRole;
import com.splan.base.bean.SysRolePermission;
import com.splan.base.bean.SysUser;
import com.splan.bplan.constants.BussinessContants;
import com.splan.bplan.dto.JurisdictionListDto;
import com.splan.bplan.dto.SysUserDto;
import com.splan.base.enums.ResultStatus;
import com.splan.bplan.http.CommonResult;
import com.splan.bplan.mappers.SysPermissionMapper;
import com.splan.bplan.mappers.SysRoleMapper;
import com.splan.bplan.mappers.SysRolePermissionMapper;
import com.splan.bplan.mappers.SysUserMapper;
import com.splan.bplan.service.BusinessOperationService;
import com.splan.bplan.service.ISysUserService;
import com.splan.bplan.service.SysUserService;
import com.splan.bplan.utils.CommonUtil;
import com.splan.bplan.utils.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Service
public class BusinessOperationServiceImpl implements BusinessOperationService {
    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    SysPermissionMapper sysPermissionMapper;
    @Autowired
    SysRoleMapper sysRoleMapper;
    @Autowired
    SysRolePermissionMapper sysRolePermissionMapper;
    @Autowired
    ISysUserService iSysUserService;
    @Override
    public CommonResult<Object> modPassward(String oldpassward, String newpassward) {
        JSONObject sysUser = (JSONObject) SecurityUtils.getSubject().getPrincipal();
        String username=sysUser.getString("username");
        sysUser=sysUserMapper.getUser(username,oldpassward);
        if(null==sysUser){
            return ResultUtil.returnError(ResultStatus.PASSWORD_NOT_BEFORE);
        }
        return ResultUtil.returnSuccess(sysUserMapper.modPassword(username,newpassward));
    }

    @Override
    public CommonResult<List<JurisdictionListDto>> jurisdictionList() {

        JSONObject sysUser = (JSONObject) SecurityUtils.getSubject().getPrincipal();
        Integer userId=sysUser.getInteger("userId");
        Integer roleid=sysUser.getInteger("roleid");
        List<SysPermission> sysPermissionList;
        if(roleid==1){
            sysPermissionList=sysPermissionMapper.getAllPermissions();
        }else{
            sysPermissionList =sysPermissionMapper.getUserPermissions(userId);
        }
        Iterator<SysPermission> iterator=sysPermissionList.iterator();
        while(iterator.hasNext()){
            String meunCode=iterator.next().getMenuCode();
            if(!meunCode.contains(BussinessContants.BUSINESSBACK))iterator.remove();
            if(meunCode.contains(BussinessContants.BUSINESSBACK_JURISDICTION))iterator.remove();
            if(meunCode.contains(BussinessContants.BUSINESSBACK_MODPASSWORD))iterator.remove();
        }
        List<JurisdictionListDto> jurisdictionListDtoList=new ArrayList<>();
           for(SysPermission sysPermission:sysPermissionList) {
//               if (sysPermission.getMenuName().equals("businessbackjurisdiction"))continue;
               JurisdictionListDto jurisdictionListDto = new JurisdictionListDto(sysPermission);
               jurisdictionListDtoList.add(jurisdictionListDto);
           }
        return ResultUtil.returnSuccess(jurisdictionListDtoList);
    }

    @Override
    public CommonResult<List<Integer>> roleJurisdictionList(String roleid) {
        QueryWrapper<SysRolePermission> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("role_id",roleid);
         List<SysRolePermission> sysRolePermissionList= sysRolePermissionMapper.selectList(queryWrapper);
        List<Integer> list=new ArrayList<>();
        for(SysRolePermission sysRolePermission:sysRolePermissionList){
            list.add(sysRolePermission.getPermissionId());
        }
        return ResultUtil.returnSuccess(list);
    }

    @Override
    public CommonResult createChildRole(String rolename,String permissionids) {
        JSONObject sysUser = (JSONObject) SecurityUtils.getSubject().getPrincipal();
        SysRole sysRole=new SysRole();
        sysRole.setRoleName(rolename);
        sysRole.setApiid(sysUser.getString("apiid"));
        sysRole.setDeleteStatus("1");
        int insert= sysRoleMapper.insert(sysRole);
        if(insert!=1){
            return ResultUtil.returnError(ResultStatus.OPERATIONFAIL);
        }
        SysRole sysRolesel=sysRoleMapper.selectById(sysRole.getId());
//       插入sys_role_permission
        List<String> permissionlist= Arrays.asList(permissionids.split("[,|，]"));
        for(String permissionid:permissionlist){
            SysRolePermission sysRolePermission=new SysRolePermission();
            sysRolePermission.setRoleId(sysRolesel.getId());
            sysRolePermission.setDeleteStatus("1");
            sysRolePermission.setPermissionId(Integer.parseInt(permissionid));
            sysRolePermissionMapper.insert(sysRolePermission);
        }
        return ResultUtil.returnSuccess(1);
    }

    @Override
    public CommonResult<List<SysRole>> childRoleList() {
        JSONObject sysUser = (JSONObject) SecurityUtils.getSubject().getPrincipal();
        String apiid=sysUser.getString("apiid");
        QueryWrapper<SysRole> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("api_id",apiid);
        return ResultUtil.returnSuccess(sysRoleMapper.selectList(queryWrapper));
    }

    @Override
    public CommonResult editChildRole(String roleid, String permissionids) {
        if(!operationRoleCheck(roleid))
            return ResultUtil.returnError(ResultStatus.OPERATIONFAIL);
//       清除该角色所有权限
        sysRolePermissionMapper.deletebyroleid(roleid);
        //       插入sys_role_permission
        List<String> permissionlist= Arrays.asList(permissionids.split("[,|，]"));
        for(String permissionid:permissionlist){
            SysRolePermission sysRolePermission=new SysRolePermission();
            sysRolePermission.setRoleId(Integer.parseInt(roleid));
            sysRolePermission.setDeleteStatus("1");
            sysRolePermission.setPermissionId(Integer.parseInt(permissionid));
            sysRolePermissionMapper.insert(sysRolePermission);
        }
        return ResultUtil.returnSuccess(1);
    }

    @Override
    public CommonResult delChildRole(String roleid) {
        if(!operationRoleCheck(roleid))
            return ResultUtil.returnError(ResultStatus.OPERATIONFAIL);
//        清除角色
        sysRoleMapper.deleteById(roleid);
//       清除角色的所有权限
        sysRolePermissionMapper.deletebyroleid(roleid);
//       该角色下所有账号状态置为不可用 有危险待加
        UpdateWrapper<SysUser> updateWrapper=new UpdateWrapper<>();
        updateWrapper.set("delete_status",2);
        updateWrapper.eq("role_id",roleid);
        iSysUserService.update(updateWrapper);
        return ResultUtil.returnSuccess(1);
    }

    @Override
    public CommonResult createChildSys(SysUserDto sysUserDto) {
        if (sysUserDto.getId() != null) {
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        }
        if (!StringUtils.equals(sysUserDto.getPassword(), sysUserDto.getSurePassword())) {
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        }
        if (StringUtils.isBlank(sysUserDto.getUsername())) {
            return  ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        }
        JSONObject sysUserJson = (JSONObject) SecurityUtils.getSubject().getPrincipal();
        String apiid=sysUserJson.getString("apiid");
        if(StringUtils.isBlank(apiid))apiid="noMatching";
        QueryWrapper<SysRole> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("api_id",apiid);
        if(sysRoleMapper.selectList(queryWrapper).size()==0){
            return ResultUtil.returnError(ResultStatus.BUSSINESS_NO_ROLE);
        }
        String jurisdiction=sysUserJson.getString("jurisdiction");
        String userId=sysUserJson.getString("userId");
        SysUser sysUser = new SysUser();
        sysUser.setNickname(sysUserDto.getRealName());
        sysUser.setUsername(sysUserDto.getUsername());
        sysUser.setPassword(sysUserDto.getPassword());
        sysUser.setRoleId(sysUserDto.getRoleId());
        sysUser.setApiid(apiid);
        sysUser.setJurisdiction(jurisdiction);
        sysUser.setFromid(Integer.parseInt(userId));
        return ResultUtil.returnSuccess(iSysUserService.save(sysUser));
    }

    @Override
    public CommonResult<List<SysUser>> childSysList() {
        JSONObject sysUserJson = (JSONObject) SecurityUtils.getSubject().getPrincipal();
        String userId=sysUserJson.getString("userId");
        List<SysUser> sysUserList=sysUserMapper.selectJoinRole(userId);
        return ResultUtil.returnSuccess(sysUserList);
    }

    @Override
    public CommonResult editChildSys(SysUser sysUser) {
        return ResultUtil.returnSuccess(sysUserMapper.updatesysuserinfo(sysUser));
    }

    @Override
    public CommonResult delChildSys(String sysuserid) {
        UpdateWrapper<SysUser> updateWrapper=new UpdateWrapper<>();
        updateWrapper.set("delete_status",2);
        updateWrapper.eq("id",sysuserid);
        return ResultUtil.returnSuccess(iSysUserService.update(updateWrapper));
    }

    private boolean operationRoleCheck(String roleid){
        SysRole sysRole=null;
        if((sysRole= sysRoleMapper.selectById(roleid))!=null){
            if(StringUtils.isBlank(sysRole.getApiid())){
                return false;
            }
        }
        return true;
    }
}
