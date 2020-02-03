package com.splan.xbet.admin.back.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.SysPermission;
import com.splan.base.bean.SysRole;
import com.splan.base.bean.SysRolePermission;
import com.splan.base.bean.SysUser;
import com.splan.base.enums.ResultStatus;
import com.splan.base.http.CommonResult;
import com.splan.xbet.admin.back.constants.Constants;
import com.splan.xbet.admin.back.constants.StormContants;
import com.splan.xbet.admin.back.dto.SysUserDto;
import com.splan.xbet.admin.back.mappers.SysPermissionMapper;
import com.splan.xbet.admin.back.mappers.SysRoleMapper;
import com.splan.xbet.admin.back.mappers.SysRolePermissionMapper;
import com.splan.xbet.admin.back.mappers.SysUserMapper;
import com.splan.xbet.admin.back.result.JurisdictionResult;
import com.splan.xbet.admin.back.service.XBetSysUserService;
import com.splan.xbet.admin.back.utils.CommonUtil;
import com.splan.xbet.admin.back.utils.ResultUtil;
import com.splan.xbet.admin.back.utils.SqlUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class XBetSysUserServiceImpl implements XBetSysUserService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SqlUtil sqlUtil;

    @Override
    public CommonResult<IPage<SysRole>> roles(Page page) {
        List<SysRole> list=sysRoleMapper.getXbetRole(page);
        page.setRecords(list);
        return ResultUtil.returnSuccess(page);
    }

    @Override
    public CommonResult addRole(SysUser sysUser,SysRole sysRole) {
        if(sysRole.getId()!=null)
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        if(StringUtils.isBlank(sysRole.getRoleDescribe()))
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        if(StringUtils.isBlank(sysRole.getDeleteStatus()))
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        sysRole.setCreateId(sysUser.getId());
        int x =sysRoleMapper.insert(sysRole);
        return ResultUtil.returnSuccess(x);
    }

    @Override
    public CommonResult editRole(SysUser sysUser, SysRole sysRole) {
        if(sysRole.getId()==null)
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        UpdateWrapper<SysRole> updateWrapper=new UpdateWrapper<>();
        updateWrapper.eq("id",sysRole.getId());
        sysRole.setModifyId(sysUser.getId());
        return ResultUtil.returnSuccess(sysRoleMapper.update(sysRole,updateWrapper));
    }

    @Override
    public CommonResult<IPage<SysUser>> sysUserByRoleId(Page page,String roleId,String status) {
        List<SysUser> list= sysUserMapper.getXbetSysUser(page,roleId,status);
        page.setRecords(list);
        return ResultUtil.returnSuccess(page);
    }

    @Override
    public CommonResult distributionSysUserConnect(String sysUserId, String roleId) {
        if(StringUtils.isBlank(sysUserId))
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        if(StringUtils.isBlank(roleId))
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        return ResultUtil.returnSuccess(sysUserMapper.updateXbetSysUserRoleId(sysUserId,roleId));
    }

    @Override
    public CommonResult distributionSysUserUnConnect(String sysUserId) {
        if(StringUtils.isBlank(sysUserId))
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        sqlUtil.clearExportTemp(sysUserId);
        return ResultUtil.returnSuccess(sysUserMapper.updateXbetSysUserRoleId(sysUserId,"0"));
    }

    @Override
    public CommonResult<List<Integer>> assignPermissions(String roleId) {
        List<Integer> result=new ArrayList<>();
        if("1".equals(roleId))result=sysPermissionMapper.getSuperIds(StormContants.STORM_SYS_PERMISSION_MIN);
        else result=sysPermissionMapper.getNormalIds(StormContants.STORM_SYS_PERMISSION_MIN,Integer.parseInt(roleId));
        return ResultUtil.returnSuccess(result);
    }

    @Override
    public CommonResult assignPermissionsGetAll() {
        Set<String> set=sysPermissionMapper.getStormMeunName(StormContants.STORM_SYS_PERMISSION_MIN);
        List<SysPermission> list=sysPermissionMapper.getAllPermissions2(StormContants.STORM_SYS_PERMISSION_MIN);
        Map<String,List<SysPermission>> map=new HashMap<>();
        for(String meunName:set){
            map.put(meunName,new ArrayList<>());
        }
        for(SysPermission sysPermission:list){
            map.get(sysPermission.getMenuName()).add(sysPermission);
        }
        List<JurisdictionResult> result=new ArrayList<>();
        for(String key:map.keySet()){
            JurisdictionResult jurisdictionResult=new JurisdictionResult();
            jurisdictionResult.setMenuName(key);
            jurisdictionResult.setSysPermissions(map.get(key));
            result.add(jurisdictionResult);
        }
        return ResultUtil.returnSuccess(result);
    }

    @Override
    public CommonResult<IPage<SysUser>> sysUserList(Page page) {
        List<SysUser> list= sysUserMapper.getXBetSysUser2(page,"1");
        Map<String,String> map=(HashMap<String,String>)redisTemplate.opsForValue().get(Constants.SHIRO_SESSION);
        for(SysUser sysUser:list){
            if(map.values().contains(sysUser.getUsername()))
                sysUser.setOnLine(true);
        }
        page.setRecords(list);
        return ResultUtil.returnSuccess(page);
    }

    @Override
    public CommonResult addSysUser(SysUser sysUserParam,SysUserDto sysUserDto) {
        if (sysUserDto.getId() != null) {
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        }
//        if (!StringUtils.equals(sysUserDto.getPassword(), sysUserDto.getSurePassword())) {
//            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
//        }
        if (StringUtils.isBlank(sysUserDto.getUsername())) {
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        }
        QueryWrapper<SysUser> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",sysUserDto.getUsername());
        List<SysUser> sysUserList= sysUserMapper.selectList(queryWrapper);
        if(sysUserList!=null&&sysUserList.size()!=0){
            return ResultUtil.returnError(ResultStatus.SYS_EXISTS);
        }
        if (StringUtils.isBlank(sysUserDto.getPassword())) {
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        }
        if(!CommonUtil.checkNotChinese(sysUserDto.getPassword()) )
            return ResultUtil.returnError(ResultStatus.OPERATIONFAIL);

        SysUser sysUser = new SysUser();
        sysUser.setNickname(sysUserDto.getRealName());
        sysUser.setUsername(sysUserDto.getUsername());
        sysUser.setPassword(sysUserDto.getPassword());
        sysUser.setDeleteStatus(sysUserDto.getDeleteStatus());
        sysUser.setRoleId(sysUserDto.getRoleId());
        sysUser.setFromid(sysUserParam.getId());
        return ResultUtil.returnSuccess(sysUserMapper.insert(sysUser));
    }

    @Override
    public CommonResult editSysUser(SysUser sysUserParam,SysUserDto sysUserDto) {
        if (sysUserDto.getId() == null) {
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        }
//        if (!StringUtils.equals(sysUserDto.getPassword(), sysUserDto.getSurePassword())) {
//            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
//        }
//        if (StringUtils.isBlank(sysUserDto.getUsername())) {
//            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
//        }
        if(!CommonUtil.checkNotChinese(sysUserDto.getPassword()) )
            return ResultUtil.returnError(ResultStatus.OPERATIONFAIL);

        SysUser sysUser = new SysUser();
        if(sysUserDto.getRealName()!=null)
            sysUser.setNickname(sysUserDto.getRealName());
        if(sysUserDto.getUsername()!=null)
            sysUser.setUsername(sysUserDto.getUsername());
        if(sysUserDto.getPassword()!=null)
            sysUser.setPassword(sysUserDto.getPassword());
        if(sysUserDto.getRoleId()!=null)
            sysUser.setRoleId(sysUserDto.getRoleId());
        if(sysUserDto.getDeleteStatus()!=null)
            sysUser.setDeleteStatus(sysUserDto.getDeleteStatus());

        sysUser.setModifyId(sysUserParam.getId());
        UpdateWrapper<SysUser> updateWrapper=new UpdateWrapper();
        updateWrapper.eq("id",sysUserDto.getId());
        return ResultUtil.returnSuccess(sysUserMapper.update(sysUser,updateWrapper));
    }

    @Override
    public CommonResult assignPermissionsEdit(String roleId, String permissions) {
        List<String> permissionlist= Arrays.asList(permissions.split("[,|，]"));
        if(!CommonUtil.checklist(permissionlist))
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        if(!checkPermissions(permissionlist))
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);

        //       清除该角色所有权限
        sysRolePermissionMapper.deletebyroleid(roleId);
        //       插入sys_role_permission
        for(String permissionid:permissionlist){
            SysRolePermission sysRolePermission=new SysRolePermission();
            sysRolePermission.setRoleId(Integer.parseInt(roleId));
            sysRolePermission.setDeleteStatus("1");
            sysRolePermission.setPermissionId(Integer.parseInt(permissionid));
            sysRolePermissionMapper.insert(sysRolePermission);
        }
        return ResultUtil.returnSuccess(ResultStatus.SUCCESS);
    }

    private  boolean checkPermissions(List<String> permissions){
        List<String> Ids=sysPermissionMapper.getIds();
        if(!Ids.containsAll(permissions))return false;
        return true;
    }

}
