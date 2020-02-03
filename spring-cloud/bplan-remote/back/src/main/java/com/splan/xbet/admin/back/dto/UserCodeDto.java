package com.splan.xbet.admin.back.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;


@Data
public class UserCodeDto {

    @NotEmpty(message = "验证码不能为空")
    private String verificationCode;

    @NotEmpty(message = "手机号不能为空")
    private String username;

    @NotEmpty(message = "手机区号不能为空")
    private String mobileArea;

    private String simplecaptcha;

    private String captchaCode;

    private String beInviteCode;

    private String registerIp;

    private String registerChannel;

    private String terminal;

    private String channel;

    private String domain;
}
