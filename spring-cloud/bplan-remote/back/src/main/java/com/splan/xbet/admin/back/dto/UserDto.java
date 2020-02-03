package com.splan.xbet.admin.back.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;


@Data
public class UserDto{

    @NotEmpty(message = "验证码不能为空")
    private String verificationCode;

    @NotEmpty(message = "密码不能为空")
    private String pwd;

    @NotEmpty(message = "手机号不能为空")
    private String mobile;

    @NotEmpty(message = "手机区号不能为空")
    private String mobileArea;

    private String beInviteCode;

    @NotEmpty(message = "真实姓名不能为空")
    private String realName;

    private String userName;

    private String qq;

    private String registerIp;

    private String registerChannel;
}
