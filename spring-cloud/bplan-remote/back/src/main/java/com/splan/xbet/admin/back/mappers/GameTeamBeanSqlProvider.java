package com.splan.xbet.admin.back.mappers;

import com.splan.base.bean.GameTeamBean;
import org.apache.ibatis.jdbc.SQL;

public class GameTeamBeanSqlProvider {

    public String insertSelective(GameTeamBean record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("game_team");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=BIGINT}");
        }
        
        if (record.getAbbr() != null) {
            sql.VALUES("abbr", "#{abbr,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            sql.VALUES("name", "#{name,jdbcType=VARCHAR}");
        }
        
        if (record.getLogo() != null) {
            sql.VALUES("logo", "#{logo,jdbcType=VARCHAR}");
        }
        
        if (record.getCountry() != null) {
            sql.VALUES("country", "#{country,jdbcType=VARCHAR}");
        }
        
        if (record.getRegion() != null) {
            sql.VALUES("region", "#{region,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            sql.VALUES("status", "#{status,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }
}