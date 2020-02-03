package com.splan.bplan.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PayWithDrawDto implements Serializable {
    private BigDecimal amount;

    private String cardId;
}
