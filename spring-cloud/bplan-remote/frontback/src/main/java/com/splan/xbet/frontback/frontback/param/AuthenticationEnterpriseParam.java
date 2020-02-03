package com.splan.xbet.frontback.frontback.param;

import lombok.Data;

@Data
public class AuthenticationEnterpriseParam {
    private Integer id;
    private Integer sysId;
    private String name;
    private String address;
    private String card;
    private String imageUrl;
    private String representative;
    private String phone;
    private String website;

}
