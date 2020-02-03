package com.splan.base.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.splan.base.enums.Status;
import lombok.Data;

import java.io.Serializable;

/**
 * game_team
 */
@TableName(value = "game_team")
@Data
public class GameTeamBean extends BaseBean implements Serializable {

    @TableId(value = "id",type = IdType.INPUT)
    private Integer id;
    /**
     * 队伍名的缩写
     */
    @TableField(value = "abbr")
    private String abbr;

    /**
     * 队伍名字
     */
    @TableField(value = "name")
    private String name;

    /**
     * 队伍logo
     */
    @TableField(value = "logo")
    private String logo;

    /**
     * 队伍的国家
     */
    @TableField(value = "country")
    private String country;

    /**
     * 队伍的赛区
     */
    @TableField(value = "region")
    private String region;

    /**
     * 队伍状态
     */
    @TableField(value = "status")
    @JsonIgnore
    private Status status;

    @TableField(exist = false)
    private Integer dataId;




}