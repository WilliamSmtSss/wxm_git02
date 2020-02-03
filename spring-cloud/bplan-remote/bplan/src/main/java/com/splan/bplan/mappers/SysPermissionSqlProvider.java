package com.splan.bplan.mappers;

import com.splan.base.bean.SysPermission;
import org.apache.ibatis.jdbc.SQL;

public class SysPermissionSqlProvider {

    public String insertSelective(SysPermission record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("sys_permission");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getMenuCode() != null) {
            sql.VALUES("menu_code", "#{menuCode,jdbcType=VARCHAR}");
        }
        
        if (record.getMenuName() != null) {
            sql.VALUES("menu_name", "#{menuName,jdbcType=VARCHAR}");
        }
        
        if (record.getPermissionCode() != null) {
            sql.VALUES("permission_code", "#{permissionCode,jdbcType=VARCHAR}");
        }
        
        if (record.getPermissionName() != null) {
            sql.VALUES("permission_name", "#{permissionName,jdbcType=VARCHAR}");
        }
        
        if (record.getRequiredPermission() != null) {
            sql.VALUES("required_permission", "#{requiredPermission,jdbcType=BIT}");
        }
        
        return sql.toString();
    }
}