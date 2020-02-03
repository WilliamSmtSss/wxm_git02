package com.splan.xbet.admin.back.mappers;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.BetOrderBean;
import org.apache.ibatis.jdbc.SQL;

public class BetOrderBeanSqlProvider {

    public String insertSelective(BetOrderBean record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("bet_order");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getBetOptionId() != null) {
            sql.VALUES("bet_option_id", "#{betOptionId,jdbcType=INTEGER}");
        }
        
        if (record.getCheckOdd() != null) {
            sql.VALUES("check_odd", "#{checkOdd,jdbcType=DECIMAL}");
        }
        
        if (record.getOdd() != null) {
            sql.VALUES("odd", "#{odd,jdbcType=DECIMAL}");
        }
        
        if (record.getAmount() != null) {
            sql.VALUES("amount", "#{amount,jdbcType=INTEGER}");
        }
        
        if (record.getEstimatedReward() != null) {
            sql.VALUES("estimated_reward", "#{estimatedReward,jdbcType=DECIMAL}");
        }
        
        if (record.getAccountCoin() != null) {
            sql.VALUES("account_coin", "#{accountCoin,jdbcType=DECIMAL}");
        }
        
        if (record.getRollbackLockCoin() != null) {
            sql.VALUES("rollback_lock_coin", "#{rollbackLockCoin,jdbcType=DECIMAL}");
        }
        
        if (record.getTenantUserNo() != null) {
            sql.VALUES("tenant_user_no", "#{tenantUserNo,jdbcType=BIGINT}");
        }
        
        if (record.getTenantOrderNo() != null) {
            sql.VALUES("tenant_order_no", "#{tenantOrderNo,jdbcType=VARCHAR}");
        }
        
        if (record.getWinLose() != null) {
            sql.VALUES("win_lose", "#{winLose,jdbcType=VARCHAR}");
        }
        
        if (record.getHedge() != null) {
            sql.VALUES("hedge", "#{hedge,jdbcType=BIT}");
        }
        
        if (record.getStatus() != null) {
            sql.VALUES("status", "#{status,jdbcType=VARCHAR}");
        }
        
        return sql.toString();
    }

    public String selectByDate(Page page, JSONObject jsonObject){
        SQL sql = new SQL();
        sql.SELECT(" a.*,b.mobile,b.username,b.real_name,b.invite_code ");
        sql.FROM("bet_order as a ");
        sql.JOIN(" user_account as b on a.tenant_user_no=b.id ");
        sql.WHERE(" 1=1 ");
        if (jsonObject.getString("startTime")!=null){
            sql.WHERE(" a.create_time>=#{jsonObject.startTime}");
        }
        if (jsonObject.getString("endTime")!=null){
            sql.WHERE(" a.create_time<=#{jsonObject.endTime}");
        }
        if (jsonObject.getString("mobile")!=null){
            sql.WHERE(" b.mobile=#{jsonObject.mobile}");
        }
        if (jsonObject.getLong("userId")!=null){
            sql.WHERE(" a.tenant_user_no=#{jsonObject.userId}");
        }
        sql.ORDER_BY(" a.create_time desc");

        return sql.toString();

    }
}