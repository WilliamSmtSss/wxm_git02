package com.splan.xbet.admin.back.result;

import com.splan.base.bean.BetTopicsBean;
import com.splan.base.bean.GameTeamBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@ApiModel
public class BetResult implements Serializable {
    private BetTopicsBean betTopics;
    private List<GameTeamBean> gameTeams;
    private Date startTime;
    private BigDecimal orderAmount;
    private Integer orderCount;
    private Integer userCount;
    private BigDecimal settledAmount;
    private String settledRate;
    private Integer gameNo;
    @ApiModelProperty(value = "投注的队伍")
    private String team;
    private Integer bet_option;
}
