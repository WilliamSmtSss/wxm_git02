package com.splan.xbet.admin.back.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BetOrderSignDto implements Serializable {

    private BetOrderDto betOrder;

    private String sign;
}
