package com.splan.xbet.admin.back.controller.xbetback;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.SysRole;
import com.splan.base.bean.SysUser;
import com.splan.base.http.CommonResult;
import com.splan.xbet.admin.back.annotation.CurrentSysUser;
import com.splan.xbet.admin.back.dto.SysUserDto;
import com.splan.xbet.admin.back.service.XBetSysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping("/back/xBet/sysUser")
@Api(tags={"xBet后台:权限管理"})
@RequiresPermissions(value = "jurisdictionManage")
public class SysUserController {

    @Autowired
    private XBetSysUserService xbetSysUserService;


    @GetMapping("/roles")
    @ApiOperation(value = "角色列表", notes = "")
    public CommonResult<IPage<SysRole>> roles(Page page) {
        return xbetSysUserService.roles(page);
    }

    @GetMapping("/addRole")
    @ApiOperation(value = "增加角色", notes = "")
    public CommonResult addRole(@CurrentSysUser @ApiIgnore SysUser sysUser,SysRole sysRole) {
        return xbetSysUserService.addRole(sysUser,sysRole);
    }

    @GetMapping("/editRole")
    @ApiOperation(value = "编辑角色", notes = "")
    public CommonResult editRole(@CurrentSysUser @ApiIgnore SysUser sysUser,SysRole sysRole) {
        return xbetSysUserService.editRole(sysUser,sysRole);
    }

    @GetMapping("/sysUserByRoleId")
    @ApiOperation(value = "组内/组外用户", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId",value = "角色ID"),
            @ApiImplicitParam(name = "status",value = "组标识 1 组内 0 组外"),
    })
    public CommonResult<IPage<SysUser>> sysUserByRoleId(Page page,String roleId,String status){
        return xbetSysUserService.sysUserByRoleId(page,roleId,status);
    }

    @GetMapping("/assignSysUserConnect")
    @ApiOperation(value = "分配用户 关联", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId",value = "角色ID"),
            @ApiImplicitParam(name = "sysUserId",value = "账号ID"),
    })
    public CommonResult distributionSysUserConnect(String sysUserId,String roleId){
        return xbetSysUserService.distributionSysUserConnect(sysUserId, roleId);
    }

    @GetMapping("/assignSysUserUnConnect")
    @ApiOperation(value = "分配用户 解除", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUserId",value = "账号ID"),
    })
    public CommonResult distributionSysUserUnConnect(String sysUserId){
        return xbetSysUserService.distributionSysUserUnConnect(sysUserId);
    }

    @GetMapping("/assignPermissionsByRoleId")
    @ApiOperation(value = "分配权限 根据角色ID获取权限Id列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId",value = "角色ID"),
    })
    public CommonResult<List<Integer>> assignPermissions(String roleId){
        return xbetSysUserService.assignPermissions(roleId);
    }

    @GetMapping("/assignPermissionsGetAll")
    @ApiOperation(value = "分配权限 获取所有权限列表", notes = "")
    public CommonResult assignPermissionsGetAll(){
        return xbetSysUserService.assignPermissionsGetAll();
    }

    @GetMapping("/sysUserList")
    @ApiOperation(value = "后台用户列表", notes = "")
    public CommonResult<IPage<SysUser>> sysUserList(Page page) {
        return xbetSysUserService.sysUserList(page);
    }

    @GetMapping("/addSysUser")
    @ApiOperation(value = "增加后台用户", notes = "")
    public CommonResult addSysUser(@CurrentSysUser @ApiIgnore SysUser sysUser,SysUserDto sysUserDto) {
        return xbetSysUserService.addSysUser(sysUser,sysUserDto);
    }

    @GetMapping("/editSysUser")
    @ApiOperation(value = "编辑后台用户", notes = "")
    public CommonResult editSysUser(@CurrentSysUser @ApiIgnore SysUser sysUser,SysUserDto sysUserDto) {
        return xbetSysUserService.editSysUser(sysUser,sysUserDto);
    }

    @GetMapping("/assignPermissionsEdit")
    @ApiOperation(value = "分配权限 编辑", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId",value = "角色ID"),
            @ApiImplicitParam(name = "permissions",value = "权限ID列表 逗号分隔"),
    })
    public CommonResult assignPermissionsEdit(String roleId,String permissions){
        return xbetSysUserService.assignPermissionsEdit(roleId, permissions);
    }
}
