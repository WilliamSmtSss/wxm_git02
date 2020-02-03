package com.splan.bplan.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PayBackDto implements Serializable {
    private BigDecimal amount;

    private String payment_reference;

    private String provider_reference;

    private String reference;

    private String status;

    private String sign;

    private String product_id;
}
