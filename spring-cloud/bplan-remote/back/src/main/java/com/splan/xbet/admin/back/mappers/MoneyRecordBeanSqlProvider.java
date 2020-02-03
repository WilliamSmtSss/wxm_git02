package com.splan.xbet.admin.back.mappers;

import com.splan.base.bean.MoneyRecordBean;
import org.apache.ibatis.jdbc.SQL;

public class MoneyRecordBeanSqlProvider {

    public String insertSelective(MoneyRecordBean record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("money_record");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=BIGINT}");
        }
        
        if (record.getBalanceId() != null) {
            sql.VALUES("balance_id", "#{balanceId,jdbcType=BIGINT}");
        }
        
        if (record.getMoneyableId() != null) {
            sql.VALUES("moneyable_id", "#{moneyableId,jdbcType=BIGINT}");
        }
        
        if (record.getMoneyableType() != null) {
            sql.VALUES("moneyable_type", "#{moneyableType,jdbcType=VARCHAR}");
        }
        
        if (record.getKind() != null) {
            sql.VALUES("kind", "#{kind,jdbcType=INTEGER}");
        }
        
        if (record.getAmount() != null) {
            sql.VALUES("amount", "#{amount,jdbcType=DECIMAL}");
        }
        
        if (record.getMoneyFrom() != null) {
            sql.VALUES("money_from", "#{moneyFrom,jdbcType=DECIMAL}");
        }
        
        if (record.getAlgorithm() != null) {
            sql.VALUES("algorithm", "#{algorithm,jdbcType=VARCHAR}");
        }
        
        if (record.getMoneyTo() != null) {
            sql.VALUES("money_to", "#{moneyTo,jdbcType=DECIMAL}");
        }
        
        if (record.getModifyId() != null) {
            sql.VALUES("modify_id", "#{modifyId,jdbcType=BIGINT}");
        }
        
        if (record.getDetail() != null) {
            sql.VALUES("detail", "#{detail,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }
}