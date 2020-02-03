package com.splan.bplan.http;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class SeriesGameNotify implements Serializable {

    private String stage;

    private Integer bo;

    private Integer gameId;

    private String status;

    private Date startTime;

    private Date endTime;

    private League league;

    private String topicableStatus;

    private String liveUrl;

    private List<Team> teams;

    private List<Score> scores;

    private List<Campaign> campaigns;

    private SeriesDetail seriesDetail;

    @Data
    public static class League{

        private Integer id;

        private String name;

        private String region;

        private String status;

        private Date startTime;

        private String gameId;

        private String level;
    }

    @Data
    public static class Team{

        private Integer id;

        private String abbr;

        private String name;

        private String logo;

        private String country;

        private String region;


    }

    @Data
    public static class Score{

        private Integer teamId;

        private Integer score;

        private Integer sequence;
    }

    @Data
    public static class Campaign{

        private Integer id;

        private Integer number;
    }

    @Data
    public static class SeriesDetail{

        private String liveUrl;

        private Integer bo;
    }

}
