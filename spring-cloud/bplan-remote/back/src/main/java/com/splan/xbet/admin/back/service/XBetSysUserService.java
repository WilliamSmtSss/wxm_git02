package com.splan.xbet.admin.back.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.SysRole;
import com.splan.base.bean.SysUser;
import com.splan.base.http.CommonResult;
import com.splan.xbet.admin.back.dto.SysUserDto;

import java.util.List;

public interface XBetSysUserService {

    CommonResult<IPage<SysRole>> roles(Page page);

    CommonResult addRole(SysUser sysUser, SysRole sysRole);

    CommonResult editRole(SysUser sysUser,SysRole sysRole);

    CommonResult<IPage<SysUser>> sysUserByRoleId(Page page,String roleId,String status);

    CommonResult distributionSysUserConnect(String sysUserId,String roleId);

    CommonResult distributionSysUserUnConnect(String sysUserId);

    CommonResult<List<Integer>> assignPermissions(String roleId);

    CommonResult assignPermissionsGetAll();

    CommonResult<IPage<SysUser>> sysUserList(Page page);

    CommonResult addSysUser(SysUser sysUser,SysUserDto sysUserDto);

    CommonResult editSysUser(SysUser sysUser,SysUserDto sysUserDto);

    CommonResult assignPermissionsEdit(String roleId,String permissions);
}
