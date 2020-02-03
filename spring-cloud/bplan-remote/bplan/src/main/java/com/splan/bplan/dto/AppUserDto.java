package com.splan.bplan.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class AppUserDto implements Serializable {

    private String extraId;//外部平台id

    /*@NotEmpty(message = "手机号不能为空")
    private String mobile;

    @NotEmpty(message = "手机区号不能为空")
    private String mobileArea;*/


    private String realName;

    private String password;

    private BigDecimal coin;

    private String apiId;

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
