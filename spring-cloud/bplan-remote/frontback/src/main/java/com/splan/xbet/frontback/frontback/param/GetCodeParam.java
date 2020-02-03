package com.splan.xbet.frontback.frontback.param;

import lombok.Data;

import javax.validation.constraints.Pattern;
import java.util.regex.Matcher;

@Data
public class GetCodeParam {

    @Pattern(regexp = "(1[3|5|7|8|]\\d{9})|()")
    public String phone;

    @Pattern(regexp = "(^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$)|()")
    public String email;

    public static void main(String[] args) {
        java.util.regex.Pattern pattern= java.util.regex.Pattern.compile("(1[3|5|7|8|]\\d{9})|()");
        Matcher matcher=pattern.matcher("1");
        System.out.println(matcher.matches());
    }

}
