package com.splan.bplan.http;

import lombok.Data;

import java.io.Serializable;

@Data
public class DataResult implements Serializable {

    private int code;

    private String result;

    private boolean success;


    public DataResult(int code,String result,boolean success){
        this.code = code;
        this.result = result;
        this.success = success;
    }
}
