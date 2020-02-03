package com.splan.bplan.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class HotGameResult implements Serializable {

    private String leagueName;

    private String vs;

    private String startTime;

    private Integer dataId;

    private Integer orderCount;
}
