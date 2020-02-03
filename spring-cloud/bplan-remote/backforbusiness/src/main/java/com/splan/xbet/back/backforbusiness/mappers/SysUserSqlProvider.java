package com.splan.xbet.back.backforbusiness.mappers;

import com.splan.base.bean.front.SysUser;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;

public class SysUserSqlProvider {

    public String insertSelective(SysUser record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("front_sys_user");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getUsername() != null) {
            sql.VALUES("username", "#{username,jdbcType=VARCHAR}");
        }
        
        if (record.getPassword() != null) {
            sql.VALUES("password", "#{password,jdbcType=VARCHAR}");
        }
        
        if (record.getNickname() != null) {
            sql.VALUES("nickname", "#{nickname,jdbcType=VARCHAR}");
        }
        
        if (record.getRoleId() != null) {
            sql.VALUES("role_id", "#{roleId,jdbcType=INTEGER}");
        }
        
        if (record.getDeleteStatus() != null) {
            sql.VALUES("delete_status", "#{deleteStatus,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    public String updateive(SysUser sysUser){
        SQL sql=new SQL();
        sql.UPDATE("front_sys_user");

        if(StringUtils.isNotBlank(sysUser.getUsername())){
            sql.SET("username = #{sysUser.username}");
        }

        if(StringUtils.isNotBlank(sysUser.getPassword())){
            sql.SET("password = #{sysUser.password}");
        }

        if(sysUser.getRoleId()!=null&&sysUser.getRoleId()!=0){
            sql.SET("roleId = #{sysUser.roleId}");
        }

        sql.WHERE("id=#{sysUser.id}");
        return sql.toString();
    }

}