package com.splan.xbet.admin.back.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RePayDepositDto implements Serializable {

    private String service;

    private String mchId;

    private String outTradeNo;

    private String attach;

    private Integer totalFee;// 分为单位

    private String mchCreateIp;

    private String notifyUrl;

    private String sign;
}
