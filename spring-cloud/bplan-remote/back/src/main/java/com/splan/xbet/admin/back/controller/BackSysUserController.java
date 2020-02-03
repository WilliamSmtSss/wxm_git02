package com.splan.xbet.admin.back.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.SysPermission;
import com.splan.base.bean.SysRole;
import com.splan.base.bean.SysUser;
import com.splan.base.http.CommonResult;
import com.splan.xbet.admin.back.dto.SysUserDto;
import com.splan.base.enums.ResultStatus;
import com.splan.xbet.admin.back.mappers.SysRoleMapper;
import com.splan.xbet.admin.back.service.*;
import com.splan.xbet.admin.back.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/back/sysUser")
@Api(value="管理员权限",tags={"运营平台"})
//@RequiresPermissions(value = {"stormJurisdictionManage"})
public class BackSysUserController {

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private ISysPermissionService sysPermissionService;

    @Autowired
    private ISysRolePermissionService sysRolePermissionService;

    @Autowired
    private BackSysUserService backSysUserService;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @GetMapping("/list")
    @ApiOperation(value = "后台用户列表", notes = "")
    public CommonResult<IPage<SysUser>> list(Page page, String userId) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("delete_status", 1);
        queryWrapper.ne("username","admin");
        if (StringUtils.isNotBlank(userId)) {
            queryWrapper.eq("id", userId);
            queryWrapper.or();
            queryWrapper.eq("username",userId);
        }
        queryWrapper.orderByDesc("create_time");
        IPage<SysUser> users = sysUserService.page(page, queryWrapper);
//        users.getRecords().forEach(sysUser -> {
//            if (sysUser.getRoleId() == 1) {
//                sysUser.setPermissions(sysPermissionService.list());
//            } else {
//                sysUser.setPermissions(sysPermissionService.getUserPermissions(sysUser.getId()));
//            }
//        });
        users.getRecords().forEach(sysUser -> {
            SysRole sysRole=sysRoleMapper.getRoleBySysuserId(sysUser.getId()+"");
            sysUser.setRolename(sysRole!=null?sysRole.getRoleName():"");
        });
        return ResultUtil.returnSuccess(users);
    }

    @PostMapping("/add")
    @ApiOperation(value = "添加后台用户", notes = "")
    public CommonResult<Boolean> add(SysUserDto sysUserDto) {
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
        Map<String,Object> map= sysUserService.getMap(queryWrapper);
        if(map!=null&&map.size()!=0){
            return ResultUtil.returnError(ResultStatus.SYS_EXISTS);
        }
        SysUser sysUser = new SysUser();
        sysUser.setNickname(sysUserDto.getRealName());
        sysUser.setUsername(sysUserDto.getUsername());
        sysUser.setPassword(sysUserDto.getPassword());
        sysUser.setRoleId(sysUserDto.getRoleId());
        return ResultUtil.returnSuccess(sysUserService.save(sysUser));
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新后台用户", notes = "")
    public CommonResult<Boolean> update(SysUserDto sysUserDto) {
        if (sysUserDto.getId() == null) {
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        }
//        if (!StringUtils.equals(sysUserDto.getPassword(), sysUserDto.getSurePassword())) {
//            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
//        }
//        if (StringUtils.isBlank(sysUserDto.getUsername())) {
//            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
//        }
        SysUser sysUser = new SysUser();
        if(sysUserDto.getRealName()!=null)
        sysUser.setNickname(sysUserDto.getRealName());
        if(sysUserDto.getUsername()!=null)
        sysUser.setUsername(sysUserDto.getUsername());
        if(sysUserDto.getPassword()!=null)
        sysUser.setPassword(sysUserDto.getPassword());
        if(sysUserDto.getRoleId()!=null)
        sysUser.setRoleId(sysUserDto.getRoleId());

        sysUser.setId(sysUserDto.getId());

        return ResultUtil.returnSuccess(sysUserService.updateById(sysUser));
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除后台用户", notes = "")
    public CommonResult<Boolean> delete(Integer id) {
        if (id == null) {
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        }
//        SysUser sysUser = sysUserService.getById(id);
//        sysUser.setDeleteStatus("2");
        return ResultUtil.returnSuccess(sysUserService.removeById(id));
    }


    @GetMapping("/roles")
    @ApiOperation(value = "角色列表", notes = "")
    public CommonResult<List<SysRole>> roles() {
        return backSysUserService.roles();
    }

    @GetMapping("/addRole")
    @ApiOperation(value = "增加角色", notes = "")
    @RequiresPermissions(value = {"stormBannerManage:addRole"})
    public CommonResult addRole(SysRole sysRole,String permissionids) {
        return backSysUserService.addRole(sysRole,permissionids);
    }

    @GetMapping("/editRole")
    @ApiOperation(value = "编辑角色", notes = "")
    public CommonResult editRole(SysRole sysRole) {
        return backSysUserService.editRole(sysRole);
    }

    @GetMapping("/delRole")
    @ApiOperation(value = "删除角色", notes = "")
    public CommonResult delRole(String roleId) {
        return backSysUserService.delRole(roleId);
    }

    @GetMapping("/addPermission")
    @ApiOperation(value = "增加权限", notes = "")
    public CommonResult addPermission(SysPermission sysPermission) {
        return backSysUserService.addPermission(sysPermission);
    }

    @GetMapping("/editPermission")
    @ApiOperation(value = "编辑权限", notes = "")
    public CommonResult editPermission(SysPermission sysPermission) {
        return  backSysUserService.editPermission(sysPermission);
    }

    @GetMapping("/delPermission")
    @ApiOperation(value = "删除权限", notes = "")
    public CommonResult delPermission(String permissionId) {
        return backSysUserService.delPermission(permissionId);
    }

    @GetMapping("/permissions")
    @ApiOperation(value = "根据账号查询权限列表", notes = "")
    public CommonResult<List<SysPermission>> permissions(Integer userId) {
        SysUser sysUser = sysUserService.getById(userId);
        List<SysPermission> list;
        if (sysUser.getRoleId() == 1) {
            list = sysPermissionService.list();
        } else {
            list = sysPermissionService.getUserPermissions(userId);
        }
        return ResultUtil.returnSuccess(list);
    }

    @GetMapping("/permissionsByRoleId")
    @ApiOperation(value = "根据角色ID查询权限列表", notes = "")
    public CommonResult<List<Integer>> permissionsByRoleId(Integer roleId) {
        return backSysUserService.permissionsByRoleId(roleId);
    }

    @GetMapping("/allPermissions")
    @ApiOperation(value = "所有权限列表", notes = "")
    public CommonResult allPermissions() {
        return backSysUserService.allPermissions();
    }

    @GetMapping("/editPermissions")
    @ApiOperation(value = "编辑角色权限", notes = "")
    @RequiresPermissions(value = {"stormBannerManage:editRole"})
    public CommonResult<List<SysPermission>> editPermissions(String roleid, String permissionids) {
        return backSysUserService.editPermissions(roleid,permissionids);
    }

//    @GetMapping("/removepermissions")
//    @ApiOperation(value = "移除权限", notes = "")
//    public CommonResult<Boolean> removePermission(Integer rid, Integer pid){
//        List<SysRolePermission> list=sysRolePermissionService.selectbyrpid(rid,pid);
//        if(list.size()!=0){
//            return ResultUtil.returnSuccess(sysRolePermissionService.update(list.get(0)));
//        }
//        return ResultUtil.returnSuccess(false);
//    }

}
