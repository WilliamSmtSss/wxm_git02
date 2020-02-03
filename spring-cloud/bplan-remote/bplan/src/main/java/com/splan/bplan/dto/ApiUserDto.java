package com.splan.bplan.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ApiUserDto implements Serializable {

    private String extraId;//外部平台id

    private String realName;

    private String password;

    private String coin;

    private String apiId;

    private Integer currency = 1;//币种 默认1 rmb



    @Override
    public String toString() {
        return "AppUserDto{" +
                "extraId='" + extraId + '\'' +
                ", realName='" + realName + '\'' +
                ", password='" + password + '\'' +
                ", coin=" + coin +
                ", apiId='" + apiId + '\'' +
                '}';
    }
}
