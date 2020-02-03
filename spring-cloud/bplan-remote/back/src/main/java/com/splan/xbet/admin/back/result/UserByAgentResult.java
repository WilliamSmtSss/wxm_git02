package com.splan.xbet.admin.back.result;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserByAgentResult implements Serializable {

    private String realName;

    private String mobile;

    //private BigDecimal reward = BigDecimal.ZERO;

    private Date lastLoginTime;

    private Integer orderTotal = 0;

    private Integer depositTotal = 0;

    private BigDecimal yl = BigDecimal.ZERO;
}
