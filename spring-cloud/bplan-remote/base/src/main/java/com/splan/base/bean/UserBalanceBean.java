package com.splan.base.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * user_balance
 */
@Data
@TableName(value = "user_balance")
public class UserBalanceBean extends BaseLongBean implements Serializable {


    /**
     * 
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 可用金额
     */
    @TableField(value = "available_coin")
    private BigDecimal availableCoin;

    /**
     * 冻结金额
     */
    @TableField(value = "frozen_coin")
    private BigDecimal frozenCoin;

    /**
     * 账户限额
     */
    @TableField(value = "lock_coin")
    private BigDecimal lockCoin;

    /**
     * 账户限额
     */
    @TableField(value = "limit_coin")
    private BigDecimal limitCoin;

    /**
     * 总金额
     */
    @TableField(value = "coin")
    private BigDecimal coin;

    /**
     * 
     */
    @Version
    @TableField(value = "version")
    @JsonIgnore
    private Integer version;

    /**
     * 充值金额
     */
    @TableField(value = "deposit_coin")
    private BigDecimal depositCoin;

    /**
     * 取款金额
     */
    @TableField(value = "withdraw_coin")
    private BigDecimal withdrawCoin;

    /**
     * 赠送金额
     */
    @TableField(value = "gift_coin")
    private BigDecimal giftCoin;

    /**
     * 佣金
     */
    @TableField(value = "brokerage_coin")
    private BigDecimal brokerageCoin;

    /**
     * 下单限额
     */
    @TableField(value = "limit_order_coin")
    private BigDecimal limitOrderCoin;

    /**
     * 累计下单
     */
    @TableField(value = "order_coin")
    private BigDecimal orderCoin;
}