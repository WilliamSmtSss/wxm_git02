package com.splan.bplan.result;

import com.splan.bplan.token.TokenModel;
import lombok.Data;

import java.io.Serializable;

@Data
public class TransferResult extends TokenModel implements Serializable {

    private String username;

    private String realName;


    public TransferResult(){

    }
}
