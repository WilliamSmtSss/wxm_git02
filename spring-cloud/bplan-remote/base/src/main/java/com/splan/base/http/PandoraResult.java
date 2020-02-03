package com.splan.base.http;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class PandoraResult<T> implements Serializable {

    private int code;

    private T result;

    private boolean success;

    private String message;


    public PandoraResult(int code,T result,boolean success){
        this.code = code;
        this.result = result;
        this.success = success;
    }

    public PandoraResult(int code,T result,String message,boolean success){
        this.code = code;
        this.result = result;
        this.success = success;
        this.message = message;
    }
}
