package com.splan.xbet.admin.back.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商家下注数据
 */
@Data
public class BetOrderDto implements Serializable {

    private Integer betOptionId;

    private Integer amount;

    private BigDecimal odd;

    private Long tenantCustomerNo;

    private String tenantOrderNo;
}
