package com.splan.bplan.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class AgentPlayerResult implements Serializable {


    private String realName;

    private String mobile;

    private Integer orderAmount;

    private Long userId;


}
