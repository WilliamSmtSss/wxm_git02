package com.splan.base.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * game_data_team
 */
@Data
@TableName(value = "game_data_team")
public class GameDataTeamBean extends BaseBean implements Serializable {
    /**
     * 
     */
    @TableField(value = "data_id")
    private Integer dataId;

    /**
     * 
     */
    @TableField(value = "team_id")
    private Integer teamId;


}