package com.splan.xbet.frontback.frontback.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class CheckCodeParam {

    @Pattern(regexp = "(1[3|5|7|8|]\\d{9})|()")
    public String phone;

    @Pattern(regexp = "(^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$)|()")
    public String email;

    @NotEmpty
    public String code;

}
