package com.splan.xbet.admin.back.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class RollbackBetNotifyDto implements Serializable {

    private String orderno;

    private String extraId;

    private Integer orderCoin;

    private BigDecimal rollbackCoin;

    private String clientId;

    private String timestamp;

    private String sign;
}
