package com.splan.xbet.admin.back.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysUserDto implements Serializable {
    private Integer id;
    private String username;
    private String realName;
    private String password;
    private String surePassword;
    private Integer roleId;
    private String apiId;
    private String deleteStatus;
}
