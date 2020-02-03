package com.splan.bplan.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BetOrderSignDto implements Serializable {

    private BetOrderDto betOrder;

    private String sign;
}
