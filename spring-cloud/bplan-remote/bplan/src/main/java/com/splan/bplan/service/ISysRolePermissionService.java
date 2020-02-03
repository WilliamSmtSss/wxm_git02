package com.splan.bplan.service;


import com.splan.base.bean.SysRolePermission;

import java.util.List;

public interface ISysRolePermissionService  {
    boolean update(SysRolePermission sysRolePermission);
    List<SysRolePermission> selectbyrpid(Integer rid,Integer pid);
}
