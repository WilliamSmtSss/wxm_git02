package com.splan.bplan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.splan.base.bean.SysPermission;
import com.splan.bplan.mappers.SysPermissionMapper;
import com.splan.bplan.service.ISysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public List<SysPermission> getUserPermissions(Integer userId) {
        return sysPermissionMapper.getUserPermissions(userId);
    }
}
