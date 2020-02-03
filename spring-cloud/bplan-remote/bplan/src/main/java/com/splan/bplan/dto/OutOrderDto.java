package com.splan.bplan.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OutOrderDto implements Serializable {

    private String orderno;

    private String extraId;

    private BigDecimal odd;

    private Integer orderCoin;

    private String clientId;

    private String orderType;

    private Integer confirm;

    private String sign;

    private String timestamp;
}
