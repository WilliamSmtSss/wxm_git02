package com.splan.xbet.admin.back.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.splan.base.bean.SysPermission;

import java.util.List;

public interface ISysPermissionService extends IService<SysPermission> {
    List<SysPermission> getUserPermissions(Integer userId);
}
