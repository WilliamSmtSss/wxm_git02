package com.splan.bplan.mappers;

import com.splan.base.bean.PayOrderBean;
import com.splan.base.enums.AccessType;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;

public class PayOrderBeanSqlProvider {

    public String insertSelective(PayOrderBean record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("pay_order");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=BIGINT}");
        }
        
        if (record.getTenantOrderNo() != null) {
            sql.VALUES("tenant_order_no", "#{tenantOrderNo,jdbcType=VARCHAR}");
        }
        
        if (record.getTenantUserNo() != null) {
            sql.VALUES("tenant_user_no", "#{tenantUserNo,jdbcType=BIGINT}");
        }
        
        if (record.getStatus() != null) {
            sql.VALUES("status", "#{status,jdbcType=VARCHAR}");
        }
        
        if (record.getAmount() != null) {
            sql.VALUES("amount", "#{amount,jdbcType=DECIMAL}");
        }
        
        if (record.getChannel() != null) {
            sql.VALUES("channel", "#{channel,jdbcType=VARCHAR}");
        }
        
        if (record.getAccessType() != null) {
            sql.VALUES("access_type", "#{accessType,jdbcType=VARCHAR}");
        }
        
        if (record.getOperatorId() != null) {
            sql.VALUES("operator_id", "#{operatorId,jdbcType=BIGINT}");
        }
        
        if (record.getOperationResult() != null) {
            sql.VALUES("operation_result", "#{operationResult,jdbcType=VARCHAR}");
        }
        
        if (record.getOperationTime() != null) {
            sql.VALUES("operation_time", "#{operationTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getCardId() != null) {
            sql.VALUES("card_id", "#{cardId,jdbcType=BIGINT}");
        }
        
        if (record.getCardNo() != null) {
            sql.VALUES("card_no", "#{cardNo,jdbcType=VARCHAR}");
        }
        
        if (record.getBankAddress() != null) {
            sql.VALUES("bank_address", "#{bankAddress,jdbcType=VARCHAR}");
        }
        
        if (record.getErrorReason() != null) {
            sql.VALUES("error_reason", "#{errorReason,jdbcType=VARCHAR}");
        }
        
        if (record.getBankCode() != null) {
            sql.VALUES("bank_code", "#{bankCode,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    public String selectOrderList() {
        SQL sql = new SQL();
        sql.SELECT("id, tenant_order_no, status, amount, channel, access_type, create_time");
        sql.FROM("pay_order");
//        sql.WHERE("access_type = #{accessType}");
//        if (null != startDate) {
//            sql.WHERE("create_time >= #{startDate}");
//        }
//        if (null != endDate) {
//            sql.WHERE("create_time <= #{endDate}");
//        }
        return sql.toString();
    }
}