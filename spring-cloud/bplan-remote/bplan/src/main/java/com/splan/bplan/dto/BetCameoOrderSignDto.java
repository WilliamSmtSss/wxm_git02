package com.splan.bplan.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BetCameoOrderSignDto implements Serializable {

    private BetCameoOrderDto cameoOrder;

    private String sign;
}
