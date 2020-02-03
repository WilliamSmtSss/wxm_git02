package com.splan.bplan.dto;

import com.splan.base.enums.WinLoseStatus;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class BetNotifyDto implements Serializable {

    private String orderno;

    private String extraId;

    private BigDecimal odd;

    private BigDecimal checkodd;

    private Integer orderCoin;

    private BigDecimal rewardCoin;

    private String clientId;

    private String orderType;

    private WinLoseStatus status;
}
