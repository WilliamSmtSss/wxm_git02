package com.splan.xbet.frontback.frontback.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class RegisterParam {

    String registerType;

    @Pattern(regexp = "(1[3|5|7|8|]\\d{9})|()")
    public String phone;

    @Pattern(regexp = "(^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$)|()")
    public String email;

    @NotEmpty
    @Pattern(regexp = "[0-9A-Za-z]{8,20}")
    String password;

    @NotEmpty
    @Pattern(regexp = "[0-9A-Za-z]{8,20}")
    String confirmPassword;

    String code;

    String bindingEmail;

    String companyName;

}
