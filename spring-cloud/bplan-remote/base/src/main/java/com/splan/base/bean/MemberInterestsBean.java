package com.splan.base.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * member_interests
 */
@Data
@TableName(value = "member_interests")
public class MemberInterestsBean extends BaseBean implements Serializable {
    /**
     * 
     */
    @TableField(value = "level_name")
    private String levelName;


    /**
     * 晋级所需累计投注额
     */
    @TableField(value = "stay_order_coin")
    private Integer StayOrderCoin;


    @TableField(value = "up_order_coin")
    private Integer upOrderCoin;

    @TableField(value = "level_start_coin")
    private Integer levelStartCoin;

    /**
     * 
     */
    @TableField(value = "water")
    private BigDecimal water;

    /**
     * 
     */
    @TableField(value = "birthday_gift")
    private Integer birthdayGift;

    /**
     * 
     */
    @TableField(value = "every_month_gift")
    private Integer everyMonthGift;

    /**
     * 
     */
    @TableField(value = "exclusive_activities")
    private Integer exclusiveActivities;

    /**
     * 
     */
    @TableField(value = "customer_service")
    private Integer customerService;

    /**
     * 
     */
    @TableField(value = "fast_access")
    private Integer fastAccess;

    /**
     * 
     */
    @TableField(value = "party")
    private Integer party;


}