package com.splan.xbet.admin.back.result;

import com.splan.base.bean.UserBean;
import com.splan.base.enums.AccessType;
import com.splan.base.enums.OperationResult;
import com.splan.base.enums.OrderStatus;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class PayOrderResult implements Serializable {
    private Long id;
    private String tenantOrderNo;
    private Long tenantUserNo;
    private OrderStatus status;
    private BigDecimal amount;
    private String channel;
    private AccessType accessType;
    private Long operatorId;
    private OperationResult operationResult;
    private Date operationTime;
    private Long cardId;
    private String cardNo;
    private String bankAddress;
    private String errorReason;
    private String bankCode;
    private Date createTime;
    private Date updateTime;
    private UserBean user;
}
