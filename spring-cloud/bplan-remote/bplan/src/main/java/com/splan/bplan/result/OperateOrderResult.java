package com.splan.bplan.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class OperateOrderResult implements Serializable {

    private String extraId;

    private String orderno;

    private String coin;
}
