package com.splan.xbet.admin.back.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OutBetOrderDetailDto implements Serializable {

    private BigDecimal odd;

    private String vsDetail;

    private Integer gameId;

    private String vs;

    //private RegisterChannel channel = RegisterChannel.H5;
}
