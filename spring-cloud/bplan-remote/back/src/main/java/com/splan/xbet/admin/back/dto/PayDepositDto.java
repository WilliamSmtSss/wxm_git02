package com.splan.xbet.admin.back.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PayDepositDto implements Serializable {

    private BigDecimal amount;

    private String payType;

    private String attach;
}
