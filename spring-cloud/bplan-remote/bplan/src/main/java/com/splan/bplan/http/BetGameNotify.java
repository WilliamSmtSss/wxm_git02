package com.splan.bplan.http;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class BetGameNotify implements Serializable {

    private int category;

    private int support;

    private boolean rollingBall;

    @JSONField(name = "switch")
    @JsonAlias(value = "switch")
    private boolean switchStatus;

    private boolean finalSwitch;

    private boolean beToRolling;

    private String status;

    private boolean cameoOpening;

    private int topicableId;

    private String topicableType;

    private Double markValue;

    private List<BetOption> betOptions;

    private List<BetResult> betResults;

    private BetLimit betLimit;


    @Data
    public static class BetOption{
        private int id;
        private float odd;
        private int sequence;
        private BigDecimal userSingleBetProfitLimit;

    }

    @Data
    public static class BetResult{
        private int id;
    }

    @Data
    public static class BetLimit{
        private int userBetLimit;
        private int userSingleBetProfitLimit;
        private int topicHandicapProfitLimit;
        private int handicapProfitLimit;
    }


}
