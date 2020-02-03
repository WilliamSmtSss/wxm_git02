package com.splan.bplan.http;

import lombok.Data;

@Data
public class BaseGameNotify {

    private String type;

    private int id;

    private String timestamp;

    private String encrypted;


}
