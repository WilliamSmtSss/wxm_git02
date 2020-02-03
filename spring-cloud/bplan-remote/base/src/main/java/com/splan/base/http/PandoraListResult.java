package com.splan.base.http;

import com.splan.base.bean.ash.AshBean;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class PandoraListResult<T extends AshBean> implements Serializable {

    private int code;

    private PandoraItemResult<T> result;

    private boolean success;

    private String message;


    public PandoraListResult(int code, PandoraItemResult result, boolean success){
        this.code = code;
        this.result = result;
        this.success = success;
    }

    public PandoraListResult(int code, PandoraItemResult result, String message, boolean success){
        this.code = code;
        this.result = result;
        this.success = success;
        this.message = message;
    }
}
