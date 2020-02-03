package com.splan.xbet.back.backforbusiness.mappers;

import com.splan.base.base.SuperMapper;
import com.splan.base.bean.front.SysRolePermission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

public interface SysRolePermissionMapper extends SuperMapper<SysRolePermission> {

    @Delete("delete from front_sys_role_permission where role_id=#{roleid}")
    int deletebyroleid(@Param("roleid") String roleid);
}