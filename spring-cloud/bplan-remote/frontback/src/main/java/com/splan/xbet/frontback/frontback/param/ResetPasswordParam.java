package com.splan.xbet.frontback.frontback.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class ResetPasswordParam extends CommonParam{

    @NotEmpty
    @Pattern(regexp = "[0-9A-Za-z]{8,20}")
    String password;

    @NotEmpty
    @Pattern(regexp = "[0-9A-Za-z]{8,20}")
    String confirmPassword;

}
