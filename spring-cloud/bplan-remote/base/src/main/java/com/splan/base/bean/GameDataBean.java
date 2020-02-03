package com.splan.base.bean;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * game_data
 */
@Data
@TableName(value = "game_data")
public class GameDataBean implements Serializable {

    @TableId(value = "id",type = IdType.INPUT)
    private Integer id;
    /**
     * 回合数 比如 BO3 的 3
     */
    @TableField(value = "bo")
    private Integer bo;

    /**
     * 回合数 比如 BO3 的 3
     */
    @TableField(value = "show_bo")
    private Integer showBo;

    /**
     * 游戏种类
     */
    @TableField(value = "game_id")
    private Integer gameId;

    /**
     * 当前游戏状态
     */
    @TableField(value = "status")
    private String status;

    /**
     * 
     */
    @TableField(value = "start_time")
    private Date startTime;

    /**
     * 
     */
    @TableField(value = "end_time")
    private Date endTime;

    /**
     * 赛事
     */
    @TableField(value = "league_id")
    private Integer leagueId;

    /**
     * 
     */
    @TableField(value = "topicable_status")
    private String topicableStatus;

    /**
     * 
     */
    @TableField(value = "progress_state")
    private String progressState;

    /**
     * 
     */
    @TableField(value = "stage")
    private String stage;

    /**
     * 
     */
    @TableField(value = "category")
    private String category;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(value = "live_url")
    private String liveUrl;


}