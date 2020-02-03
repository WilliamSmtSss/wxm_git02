package com.splan.xbet.admin.back.result;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderResult implements Serializable {

    private String groupName;

    private String vsDetail;

    private Date gameDate;

    private String odd;

    private BigDecimal amount;

    private BigDecimal estimatedReward;

    private boolean success;

}
