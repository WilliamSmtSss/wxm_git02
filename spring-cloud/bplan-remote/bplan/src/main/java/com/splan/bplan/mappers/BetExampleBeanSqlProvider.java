package com.splan.bplan.mappers;

import com.splan.base.bean.BetExampleBean;
import org.apache.ibatis.jdbc.SQL;

public class BetExampleBeanSqlProvider {

    public String insertSelective(BetExampleBean record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("bet_example");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getTopicableType() != null) {
            sql.VALUES("topicable_type", "#{topicableType,jdbcType=VARCHAR}");
        }
        
        if (record.getCategory() != null) {
            sql.VALUES("category", "#{category,jdbcType=INTEGER}");
        }
        
        if (record.getExplainText() != null) {
            sql.VALUES("explain_text", "#{explainText,jdbcType=VARCHAR}");
        }
        
        if (record.getSupport() != null) {
            sql.VALUES("support", "#{support,jdbcType=INTEGER}");
        }
        
        if (record.getExplainText2() != null) {
            sql.VALUES("explain_text2", "#{explainText2,jdbcType=VARCHAR}");
        }
        
        if (record.getExample() != null) {
            sql.VALUES("example", "#{example,jdbcType=VARCHAR}");
        }
        

        
        return sql.toString();
    }
}