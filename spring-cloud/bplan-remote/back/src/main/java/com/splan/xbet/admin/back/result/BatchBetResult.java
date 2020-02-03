package com.splan.xbet.admin.back.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class BatchBetResult implements Serializable {

    private Integer successCount;

    private Integer failCount;
}
