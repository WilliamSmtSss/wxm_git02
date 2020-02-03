package com.splan.bplan.service;

import com.splan.base.bean.SysRole;
import com.splan.base.bean.SysUser;
import com.splan.bplan.dto.JurisdictionListDto;
import com.splan.bplan.dto.SysUserDto;
import com.splan.base.enums.ResultStatus;
import com.splan.bplan.http.CommonResult;

import java.util.List;

public interface BusinessOperationService {
    CommonResult<Object> modPassward(String oldpassward,String newpassward);

    CommonResult<List<JurisdictionListDto>> jurisdictionList();

    CommonResult<List<Integer>> roleJurisdictionList(String roleid);

    CommonResult<ResultStatus> createChildRole(String rolename,String permissionids);

    CommonResult<List<SysRole>> childRoleList();

    CommonResult editChildRole(String roleid,String permissionids);

    CommonResult delChildRole(String roleid);

    CommonResult createChildSys(SysUserDto sysUserDto);

    CommonResult<List<SysUser>> childSysList();

    CommonResult editChildSys(SysUser sysUser);

    CommonResult delChildSys(String sysuserid);
}
