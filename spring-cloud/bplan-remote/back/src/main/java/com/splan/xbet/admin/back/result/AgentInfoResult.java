package com.splan.xbet.admin.back.result;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class AgentInfoResult implements Serializable {

    private BigDecimal monthReward;

    private Integer activeUser;

    private BigDecimal reward;
}
