package com.splan.base.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.splan.base.enums.AccessType;
import com.splan.base.enums.OperationResult;
import com.splan.base.enums.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * pay_order
 */
@Data
@TableName(value = "pay_order")
public class PayOrderBean extends BaseLongBean {
    /**
     * 订单号
     */
    @TableField(value = "tenant_order_no")
    private String tenantOrderNo;

    /**
     * 用户id
     */
    @TableField(value = "tenant_user_no")
    private Long tenantUserNo;

    /**
     * 订单状态
     */
    @TableField(value = "status")
    private OrderStatus status;

    /**
     * 金额
     */
    @TableField(value = "amount")
    private BigDecimal amount;

    /**
     * 渠道
     */
    @TableField(value = "channel")
    private String channel;

    /**
     * 存款取款标记
     */
    @TableField(value = "access_type")
    private AccessType accessType;

    /**
     * 审核人id
     */
    @TableField(value = "operator_id")
    private Integer operatorId;

    /**
     * 审核结果
     */
    @TableField(value = "operation_result")
    private OperationResult operationResult;

    /**
     * 审核时间
     */
    @TableField(value = "operation_time")
    private Date operationTime;

    /**
     * 银行卡id
     */
    @TableField(value = "card_id")
    private Long cardId;

    /**
     * 卡号
     */
    @TableField(value = "card_no")
    private String cardNo;

    /**
     * 分行地址
     */
    @TableField(value = "bank_address")
    private String bankAddress;

    /**
     * 异常原因
     */
    @TableField(value = "error_reason")
    private String errorReason;

    /**
     * 银行代号
     */
    @TableField(value = "bank_code")
    private String bankCode;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss EEEE", locale = "zh", timezone = "GMT+8")
    private Date createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(exist = false)
    private String bankName;
}