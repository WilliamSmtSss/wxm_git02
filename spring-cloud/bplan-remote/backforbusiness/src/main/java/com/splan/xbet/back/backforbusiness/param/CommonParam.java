package com.splan.xbet.back.backforbusiness.param;

import lombok.Data;

import javax.validation.constraints.Pattern;
@Data
public class CommonParam {

    @Pattern(regexp = "(1[3|5|7|8|]\\d{9})|()")
    public String phone;

    @Pattern(regexp = "(^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$)|()")
    public String email;

}
