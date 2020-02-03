package com.splan.xbet.admin.back.mappers;

import com.splan.xbet.admin.back.base.SuperMapper;
import com.splan.base.bean.SysRolePermission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

public interface SysRolePermissionMapper extends SuperMapper<SysRolePermission> {

    @Delete("delete from sys_role_permission where role_id=#{roleid}")
    int deletebyroleid(@Param("roleid") String roleid);
}