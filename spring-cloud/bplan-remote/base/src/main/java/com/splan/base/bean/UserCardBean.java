package com.splan.base.bean;

import com.baomidou.mybatisplus.annotation.*;
import com.splan.base.enums.Status;
import lombok.Data;

import java.util.Date;

/**
 * user_card
 */
@Data
@TableName(value = "user_card")
public class UserCardBean {
    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * user_account
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 
     */
    @TableField(value = "bank_code")
    private String bankCode;

    /**
     * 
     */
    @TableField(value = "bank_name")
    private String bankName;

    /**
     * 银行卡状态 0 正常  1冻结
     */
    @TableField(value = "status")
    private Status status;

    /**
     * 
     */
    @TableField(value = "bank_id")
    private Integer bankId;

    /**
     * 银行卡
     */
    @TableField(value = "credit_card")
    private String creditCard;

    @TableField(value = "bank_icon")
    private String bankIcon;

    @TableField(value = "bank_province")
    private String bankProvince;

    @TableField(value = "bank_city")
    private String bankCity;

    @TableField(value = "bank_county")
    private String bankCounty;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    protected Date createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    protected Date updateTime;
}