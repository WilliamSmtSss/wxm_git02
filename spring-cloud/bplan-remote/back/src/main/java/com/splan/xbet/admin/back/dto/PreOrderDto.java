package com.splan.xbet.admin.back.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PreOrderDto implements Serializable {

    private String orderno;

    private String extraId;

    private BigDecimal odd;

    private Integer orderCoin;

    private String clientId;

    private String orderType;

    private String sign;

    private String timestamp;

    private ExtraDto extra;
}
