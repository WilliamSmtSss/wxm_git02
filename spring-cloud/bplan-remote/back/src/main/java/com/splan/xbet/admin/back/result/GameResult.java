package com.splan.xbet.admin.back.result;

import com.splan.base.bean.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class GameResult implements Serializable {

    public GameResult(){

    }

    private Integer id;

    private String stage;

    private Integer bo;

    private Integer gameId;

    private String status;

    private String startTime;

    private String endTime;

    private GameLeagueBean league;

    private String rollingStatus = "no";

    private List<GameScoreBean> scores;

    @ApiModelProperty(value = "campaign.id\tint\t\t比赛ID\n" +
            "campaign.number\tint\t\t比赛顺序")
    private List<GameCampaignBean> campaigns;

    private List<GameTeamBean> teams;

    private BetTopicsBean odds;

    private Integer betCount;

    private String liveUrl;

    private GameTypeBean gameTypeBean;

    private Integer orderCount;

    private Integer beToRolling=0;

    private Integer rollingBall=0;

    //private List<GameOddsBean> odds;//







}
