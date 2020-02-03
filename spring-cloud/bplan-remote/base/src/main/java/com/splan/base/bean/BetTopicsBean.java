package com.splan.base.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.splan.base.annotation.ExcelAnnotation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * bet_topics
 */
@TableName(value = "bet_topics")
@Data
@ApiModel
public class BetTopicsBean extends BaseBean implements Serializable {

    @TableId(value = "id",type = IdType.INPUT)
    @ExcelAnnotation(id=1,name={"盘口ID"},width = 5000)
    private Integer id;
    /**
     * 枚举盘口字段
     */
    @TableField(value = "category")
    private Integer category;

    /**
     * 枚举辅助字段
     */
    @TableField(value = "support")
    private Integer support;

    /**
     * 0滚球盘 1初盘
     */
    @TableField(value = "rolling_ball")
    private boolean rollingBall;

    /**
     * 0开盘 1封盘
     */
    @TableField(value = "switch_status")
    private boolean switchStatus;

    /**
     * 盘口关盘(关闭后不可再打开)
     */
    @TableField(value = "final_switch")
    private boolean finalSwitch;

    /**
     * 滚球盘或初盘状态
     */
    @TableField(value = "status")
    private String status;

    /**
     * 
     */
    @TableField(value = "topicable_id")
    private Integer topicableId;

    /**
     * 
     */
    @TableField(value = "topicable_type")
    private String topicableType;


    @TableField(exist=false)
    private List<BetOptionBean> betOptions;

    @TableField(value = "group_name")
    private String groupName;

    @TableField(value = "be_to_rolling")
    private boolean beToRolling;

    /**
     * 可串场 true:是 false:否
     */
    @TableField(value = "cameo_opening")
    private boolean cameoOpening;

    @TableField(value = "data_id")
    @ApiModelProperty(value = "比赛ID")
    @ExcelAnnotation(id=1,name={"比赛ID"},width = 5000)
    private Integer dataId;

    @TableField(value = "mark_value")
    private Double markValue;

    @TableField(value = "user_bet_limit")
    private Integer userBetLimit;

    @TableField(value = "user_single_bet_profit_limit")
    private Integer userSingleBetProfitLimit;

    //用户单盘口下注利润限额
    @TableField(value = "topic_handicap_profit_limit")
    private Integer topicHandicapProfitLimit;

    @TableField(exist=false)
    private Integer number;

    @TableField(exist = false)
    private String nameEn;

    @TableField(exist = false)
    private Integer optionid;

    @TableField(exist = false)
    private Integer sequence;

    @TableField(exist = false,value = "orderamount")
    private Integer orderamount;

    //xBet
    @TableField(exist = false,value = "league_id")
    private String leagueId;

    @TableField(exist = false,value = "name_en")
    private String gameEnName;

    @ApiModelProperty(value = "盘口详情")
    @TableField(exist = false)
    @ExcelAnnotation(id=1,name={"盘口详情"},width = 5000)
    private String betInfo;

    @ApiModelProperty(value = "比赛信息")
    @TableField(exist = false)
    @ExcelAnnotation(id=1,name={"比赛信息"},width = 5000)
    private String dataInfo;

    @ApiModelProperty(value = "比赛时间")
    @TableField(exist = false,value = "start_time")
    @ExcelAnnotation(id=1,name={"比赛时间"},width = 5000)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date startTime;

    @ApiModelProperty(value = "状态")
    @ExcelAnnotation(id=1,name={"状态"},width = 5000)
    @TableField(exist = false)
    private String betStatus;

    @ApiModelProperty(value = "注单数")
    @TableField(exist = false)
    @ExcelAnnotation(id=1,name={"注单数"},width = 5000)
    private String orderCount;

    @ApiModelProperty(value = "总下注额")
    @TableField(exist = false)
    @ExcelAnnotation(id=1,name={"总下注额"},width = 5000)
    private String orderAmountX;

    @ApiModelProperty(value = "下注用户数")
    @TableField(exist = false)
    @ExcelAnnotation(id=1,name={"下注用户数"},width = 5000)
    private String orderUserCount;

    @ApiModelProperty(value = "总返回额")
    @TableField(exist = false)
    @ExcelAnnotation(id=1,name={"总返回额"},width = 5000)
    private String returnAmountX;

    @ApiModelProperty(value = "盈亏")
    @TableField(exist = false)
    @ExcelAnnotation(id=1,name={"盈亏"},width = 5000)
    private String orderProfitX;

    @ApiModelProperty(value = "盈利率")
    @TableField(exist = false)
    @ExcelAnnotation(id=1,name={"盈利率"},width = 5000)
    private String orderProfitRate;

    @ApiModelProperty(value = "赛果")
    @TableField(exist = false)
    @ExcelAnnotation(id=1,name={"赛果"},width = 5000)
    private String betResult;

    @ApiModelProperty(value = "游戏id")
    @TableField(exist = false)
    private Integer gameId;

}