package com.splan.bplan.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BalanceDto implements Serializable {

    private String extraId;

    private String apiId;

    private String timestamp;

    private Integer currency = 1;//币种 默认1 rmb

    public BalanceDto(){

    }

    public BalanceDto(String extraId, String apiId){
        this.extraId = extraId;
        this.apiId = apiId;
    }
}
