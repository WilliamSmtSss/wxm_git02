package com.splan.xbet.frontback.frontback.mappers;

import com.splan.base.base.SuperMapper;
import com.splan.xbet.frontback.frontback.beans.SysRolePermission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

public interface SysRolePermissionMapper extends SuperMapper<SysRolePermission> {

    @Delete("delete from back_sys_role_permission where role_id=#{roleid}")
    int deletebyroleid(@Param("roleid") String roleid);
}