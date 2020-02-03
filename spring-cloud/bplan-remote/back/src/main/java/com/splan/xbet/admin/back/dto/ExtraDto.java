package com.splan.xbet.admin.back.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ExtraDto implements Serializable {

    private List<OutBetOrderDetailDto> orderDetails;
}
