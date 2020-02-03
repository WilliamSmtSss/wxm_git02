package com.splan.xbet.admin.back.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BetCameoOrderDto implements Serializable {

    private Integer amount;

    private Integer tenantCustomerNo;

    private String tenantOrderNo;

    private List<OrderDto> betOptionCameoOrdersAttributes;
}
