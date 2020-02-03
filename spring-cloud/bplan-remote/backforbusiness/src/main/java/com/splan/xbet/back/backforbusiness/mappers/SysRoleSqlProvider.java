package com.splan.xbet.back.backforbusiness.mappers;

import com.splan.base.bean.front.SysRole;
import org.apache.ibatis.jdbc.SQL;

public class SysRoleSqlProvider {

    public String insertSelective(SysRole record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("front_sys_role");
        
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