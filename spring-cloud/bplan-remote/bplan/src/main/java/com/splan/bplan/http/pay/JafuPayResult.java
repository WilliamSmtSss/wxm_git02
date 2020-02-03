package com.splan.bplan.http.pay;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class JafuPayResult {
    private String orderid;

    private String opstate;

    private BigDecimal ovalue;

    private String sign;

    private String sysorderid;

    private Date systime;

    private String attach;

    private String msg;
}
