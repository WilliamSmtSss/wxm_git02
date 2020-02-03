package com.splan.bplan.result;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SingleBetResult implements Serializable {

    private String extraId;

    private String orderno;

    private BigDecimal coin;
}
