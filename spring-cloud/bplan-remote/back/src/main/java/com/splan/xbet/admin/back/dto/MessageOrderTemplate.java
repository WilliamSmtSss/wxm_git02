package com.splan.xbet.admin.back.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class MessageOrderTemplate implements Serializable {

    private BigDecimal amount;

    private String comment;

    private String orderNO;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date orderDate;

    private String vsDetail;

}
