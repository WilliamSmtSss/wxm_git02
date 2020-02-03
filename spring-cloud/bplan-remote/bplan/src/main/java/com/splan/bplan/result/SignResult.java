package com.splan.bplan.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class SignResult implements Serializable {

    private Long rank;

    private boolean flag;
}
