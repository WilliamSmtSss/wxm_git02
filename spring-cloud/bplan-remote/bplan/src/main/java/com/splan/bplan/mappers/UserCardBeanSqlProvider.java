package com.splan.bplan.mappers;

import com.splan.base.bean.UserCardBean;
import org.apache.ibatis.jdbc.SQL;

public class UserCardBeanSqlProvider {

    public String insertSelective(UserCardBean record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("user_card");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=BIGINT}");
        }
        
        if (record.getUserId() != null) {
            sql.VALUES("user_id", "#{userId,jdbcType=BIGINT}");
        }
        
        if (record.getBankCode() != null) {
            sql.VALUES("bank_code", "#{bankCode,jdbcType=VARCHAR}");
        }
        
        if (record.getBankName() != null) {
            sql.VALUES("bank_name", "#{bankName,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            sql.VALUES("status", "#{status,jdbcType=VARCHAR}");
        }
        
        if (record.getBankId() != null) {
            sql.VALUES("bank_id", "#{bankId,jdbcType=BIGINT}");
        }
        
        if (record.getCreditCard() != null) {
            sql.VALUES("credit_card", "#{creditCard,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }
}