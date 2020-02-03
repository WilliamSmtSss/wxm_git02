package com.splan.xbet.back.backforbusiness.param;

import lombok.Data;
import org.omg.CORBA.INTERNAL;

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
