package com.splan.xbet.admin.back.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class WalletDto implements Serializable {

    private String extraId;

    private String clientId;

    private String sign;

    public WalletDto(){

    }

    public WalletDto(String extraId,String clientId){
        this.extraId = extraId;
        this.clientId = clientId;
    }
}
