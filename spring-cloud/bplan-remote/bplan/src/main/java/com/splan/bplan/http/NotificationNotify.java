package com.splan.bplan.http;

import lombok.Data;

import java.io.Serializable;

@Data
public class NotificationNotify implements Serializable {

    private String notifyType;

    private String createdAt;

    private BodyFrom bodyFrom;


    @Data
    public static class BodyFrom{

        private Integer seriesId;

        private Integer number;

        private Integer operation;

        private Integer reason;

        private String flag;

        private String startTime;

        private String endTime;


    }
}
