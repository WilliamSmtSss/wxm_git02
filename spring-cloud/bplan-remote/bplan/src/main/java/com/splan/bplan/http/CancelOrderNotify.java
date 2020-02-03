package com.splan.bplan.http;

import lombok.Data;

import java.io.Serializable;

@Data
public class CancelOrderNotify implements Serializable {

    private String recordAt;

    private String[] ordersNumber;

}
