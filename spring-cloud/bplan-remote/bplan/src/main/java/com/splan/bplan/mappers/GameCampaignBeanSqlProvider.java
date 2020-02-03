package com.splan.bplan.mappers;

import com.splan.base.bean.GameCampaignBean;
import org.apache.ibatis.jdbc.SQL;

public class GameCampaignBeanSqlProvider {

    public String insertSelective(GameCampaignBean record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("game_campaign");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=BIGINT}");
        }
        

        if (record.getDataId() != null) {
            sql.VALUES("data_id", "#{dataId,jdbcType=BIGINT}");
        }
        
        if (record.getNumber() != null) {
            sql.VALUES("number", "#{number,jdbcType=INTEGER}");
        }
        
        return sql.toString();
    }
}