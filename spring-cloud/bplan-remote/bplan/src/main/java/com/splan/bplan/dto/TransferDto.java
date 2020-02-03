package com.splan.bplan.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TransferDto implements Serializable {

    private String extraId;//外部平台id

    private String coin;

    private String apiId;

    private String action = "IN";//  IN OUT

    private String transferNo;

    private Integer currency = 1;//币种 默认1 rmb

    private String timestamp;
}
