package com.splan.bplan.result;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by lyn on 2019/1/18.
 */
@Data
public class BankResult implements Serializable {
    private Integer id;

    private String bankName;

    private String bankCode;

    private String bankIcon;
}
