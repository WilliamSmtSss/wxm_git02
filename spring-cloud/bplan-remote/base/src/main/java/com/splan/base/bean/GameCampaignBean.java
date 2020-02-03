package com.splan.base.bean;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * game_campaign
 */
@Data
@TableName(value = "game_campaign")
public class GameCampaignBean implements Serializable {

    @TableId(value = "id",type = IdType.INPUT)
    private Integer id;

    /**
     * 
     */
    @TableField(value = "data_id")
    private Integer dataId;

    /**
     * 比赛顺序
     */
    @TableField(value = "number")
    private Integer number;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(exist = false)
    List<BetTopicsBean> betTopics;


}