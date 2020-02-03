package com.splan.xbet.admin.back.result;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.io.Serializable;

@Data
public class PayOrderStatusResult implements Serializable {

    @JsonAlias("returnType")
    private String returnType;

    private String payment_reference;

    private String message;

    private String status;

    private String amount;
}
