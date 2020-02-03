package com.splan.bplan.mappers;

import com.splan.base.bean.SysRole;
import org.apache.ibatis.jdbc.SQL;

public class SysRoleSqlProvider {

    public String insertSelective(SysRole record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("sys_role");
        
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