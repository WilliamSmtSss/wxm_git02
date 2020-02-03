package com.splan.xbet.admin.back.service;

import com.splan.base.bean.SysPermission;
import com.splan.base.bean.SysRole;
import com.splan.base.http.CommonResult;

import java.util.List;

public interface BackSysUserService {
    CommonResult<List<SysRole>> roles();

    CommonResult<List<Integer>> permissionsByRoleId(Integer roleId);

    CommonResult allPermissions();

    CommonResult<List<SysPermission>> editPermissions(String roleid, String permissionids);

    //角色，权限增删改操作
    CommonResult addRole(SysRole sysRole, String permissionids);

    CommonResult editRole(SysRole sysRole);

    CommonResult delRole(String roleId);

    CommonResult addPermission(SysPermission sysPermission);

    CommonResult editPermission(SysPermission sysPermission);

    CommonResult delPermission(String permissionId);
}
