package com.splan.bplan.mappers;

import com.splan.base.bean.GameScoreBean;
import org.apache.ibatis.jdbc.SQL;

public class GameScoreBeanSqlProvider {

    public String insertSelective(GameScoreBean record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("game_score");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=BIGINT}");
        }
        
        if (record.getTeamId() != null) {
            sql.VALUES("team_id", "#{teamId,jdbcType=BIGINT}");
        }
        
        if (record.getScore() != null) {
            sql.VALUES("score", "#{score,jdbcType=INTEGER}");
        }
        
        if (record.getDataId() != null) {
            sql.VALUES("data_id", "#{dataId,jdbcType=BIGINT}");
        }
        
        if (record.getSequence() != null) {
            sql.VALUES("sequence", "#{sequence,jdbcType=INTEGER}");
        }
        
        return sql.toString();
    }
}