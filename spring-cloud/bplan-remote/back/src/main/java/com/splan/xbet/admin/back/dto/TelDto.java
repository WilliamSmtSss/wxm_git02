package com.splan.xbet.admin.back.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TelDto implements Serializable {

    private String mobile;

    private String verificationCode;

    private String type;

    private String registerIp;


}
