package com.splan.bplan.mappers;

import com.splan.base.bean.GameDataTeamBean;
import org.apache.ibatis.jdbc.SQL;

public class GameDataTeamBeanSqlProvider {

    public String insertSelective(GameDataTeamBean record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("game_data_team");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getDataId() != null) {
            sql.VALUES("data_id", "#{dataId,jdbcType=INTEGER}");
        }
        
        if (record.getTeamId() != null) {
            sql.VALUES("team_id", "#{teamId,jdbcType=INTEGER}");
        }
        
        return sql.toString();
    }
}