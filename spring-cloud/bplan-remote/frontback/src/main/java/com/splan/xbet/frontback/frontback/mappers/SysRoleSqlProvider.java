package com.splan.xbet.frontback.frontback.mappers;

import com.splan.xbet.frontback.frontback.beans.SysRole;
import org.apache.ibatis.jdbc.SQL;

public class SysRoleSqlProvider {

    public String insertSelective(SysRole record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("back_sys_role");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getRoleName() != null) {
            sql.VALUES("role_name", "#{roleName,jdbcType=VARCHAR}");
        }
        
        if (record.getDeleteStatus() != null) {
            sql.VALUES("delete_status", "#{deleteStatus,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }
}