package com.splan.xbet.admin.back.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BetBatchNotifyDto implements Serializable {

    private Integer total;

    private String timestamp;

    private String serialNo;

    private String clientId;

    private String sign;

    private List<BetNotifyDto> list;
}
