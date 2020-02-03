package com.splan.bplan.mappers;

import com.splan.base.bean.CommonBankBean;
import org.apache.ibatis.jdbc.SQL;

public class CommonBankBeanSqlProvider {

    public String insertSelective(CommonBankBean record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("common_bank");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getBankName() != null) {
            sql.VALUES("bank_name", "#{bankName,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            sql.VALUES("status", "#{status,jdbcType=VARCHAR}");
        }
        
        if (record.getBankCode() != null) {
            sql.VALUES("bank_code", "#{bankCode,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }
}