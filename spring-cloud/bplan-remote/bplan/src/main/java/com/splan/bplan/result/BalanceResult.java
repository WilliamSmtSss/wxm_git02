package com.splan.bplan.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class BalanceResult implements Serializable {

    private String extraId;

    private String apiId;

    private String createTime;

    private String balance;

    private String status = "ENABLE";//是否被禁用

    private Integer currency = 1;//币种 默认1 rmb
}
