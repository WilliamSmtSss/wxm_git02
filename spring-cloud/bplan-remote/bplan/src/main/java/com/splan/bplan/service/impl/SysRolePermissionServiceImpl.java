package com.splan.bplan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.splan.base.bean.SysRolePermission;
import com.splan.bplan.mappers.SysRolePermissionMapper;
import com.splan.bplan.service.ISysRolePermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements ISysRolePermissionService {
    @Override
    public boolean update(SysRolePermission sysRolePermission) {
        UpdateWrapper<SysRolePermission> up=new UpdateWrapper<>();
        up.eq("role_id",sysRolePermission.getRoleId());
        up.eq("permission_id",sysRolePermission.getPermissionId());
        sysRolePermission.setDeleteStatus("2");
        return update(sysRolePermission,up);
    }

    @Override
    public List<SysRolePermission> selectbyrpid(Integer rid, Integer pid) {
        QueryWrapper<SysRolePermission> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("role_id",rid);
        queryWrapper.eq("permission_id_id",pid);
        return list(queryWrapper);
    }
}
