package com.splan.xbet.admin.back.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class PasswordDto implements Serializable {
    @NotEmpty(message = "验证码不能为空")
    private String verificationCode;

    @NotEmpty(message = "手机号不能为空")
    private String mobile;

    @NotEmpty(message = "手机区号不能为空")
    private String mobileArea;

    private String beforePassword;

    @NotEmpty(message = "新密码不能为空")
    private String newPassword;

    private String surePassword;
}
