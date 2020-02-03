package com.splan.bplan.mappers;

import com.splan.base.bean.SysRolePermission;
import org.apache.ibatis.jdbc.SQL;

public class SysRolePermissionSqlProvider {

    public String insertSelective(SysRolePermission record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("sys_role_permission");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getRoleId() != null) {
            sql.VALUES("role_id", "#{roleId,jdbcType=INTEGER}");
        }
        
        if (record.getPermissionId() != null) {
            sql.VALUES("permission_id", "#{permissionId,jdbcType=INTEGER}");
        }
        
        if (record.getDeleteStatus() != null) {
            sql.VALUES("delete_status", "#{deleteStatus,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }
}