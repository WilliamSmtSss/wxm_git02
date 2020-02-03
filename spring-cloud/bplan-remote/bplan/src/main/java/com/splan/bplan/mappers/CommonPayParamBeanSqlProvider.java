package com.splan.bplan.mappers;

import com.splan.base.bean.CommonPayParamBean;
import org.apache.ibatis.jdbc.SQL;

public class CommonPayParamBeanSqlProvider {

    public String insertSelective(CommonPayParamBean record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("common_pay_param");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getType() != null) {
            sql.VALUES("type", "#{type,jdbcType=VARCHAR}");
        }
        
        if (record.getApiId() != null) {
            sql.VALUES("api_id", "#{apiId,jdbcType=VARCHAR}");
        }
        
        if (record.getApiKey() != null) {
            sql.VALUES("api_key", "#{apiKey,jdbcType=VARCHAR}");
        }
        
        if (record.getApiUrl() != null) {
            sql.VALUES("api_url", "#{apiUrl,jdbcType=VARCHAR}");
        }
        
        if (record.getCallbackUrl() != null) {
            sql.VALUES("callback_url", "#{callbackUrl,jdbcType=VARCHAR}");
        }
        
        if (record.getHrefbackUrl() != null) {
            sql.VALUES("hrefback_url", "#{hrefbackUrl,jdbcType=VARCHAR}");
        }
        
        if (record.getComment() != null) {
            sql.VALUES("comment", "#{comment,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            sql.VALUES("status", "#{status,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }
}