package com.splan.xbet.admin.back.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderBetDto implements Serializable {

    @NotEmpty(message = "订单不能为空")
    private List<OrderDto> orders;

    private boolean confirm;

    @Min(value = 2,message = "下注金额不能低于2")
    private int totalAmount;

    private BigDecimal odds;

    /**
     * true串关  false不串
     */
    private boolean cameoOrder;

    /**
     * 串单
     */
    private String cameo;

    private String ip;
}
