package com.splan.xbet.admin.back.result;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AgentPlayerResult implements Serializable {


    private String realName;

    private String mobile;

    private Integer orderAmount;

    private Long userId;

    private Date lastLoginTime;


}
