package com.splan.xbet.admin.back.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TelCallbackDto implements Serializable {

    private String result_code;

    private String result_msg;

    private String mch_id;

    private String transaction_id;

    private String out_trade_no;

    private String attach;

    private String time_end;

    private String sign;
}
