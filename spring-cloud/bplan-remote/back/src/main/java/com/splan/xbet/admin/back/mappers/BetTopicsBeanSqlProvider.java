package com.splan.xbet.admin.back.mappers;

import com.splan.base.bean.BetTopicsBean;
import org.apache.ibatis.jdbc.SQL;

public class BetTopicsBeanSqlProvider {

    public String insertSelective(BetTopicsBean record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("bet_topics");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getCategory() != null) {
            sql.VALUES("category", "#{category,jdbcType=INTEGER}");
        }
        
        if (record.getSupport() != null) {
            sql.VALUES("support", "#{support,jdbcType=INTEGER}");
        }

        
        if (record.getStatus() != null) {
            sql.VALUES("status", "#{status,jdbcType=VARCHAR}");
        }
        
        if (record.getTopicableId() != null) {
            sql.VALUES("topicable_id", "#{topicableId,jdbcType=INTEGER}");
        }
        
        if (record.getTopicableType() != null) {
            sql.VALUES("topicable_type", "#{topicableType,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }
}