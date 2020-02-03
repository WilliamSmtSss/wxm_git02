package com.splan.base.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * game_score
 */
@Data
@TableName(value = "game_score")
public class GameScoreBean extends BaseBean implements Serializable {

//    @TableId(value = "id",type = IdType.INPUT)
//    private Integer id;
    /**
     * 队伍ID
     */
    @TableField(value = "team_id")
    private Integer teamId;

    /**
     * 队伍的得分
     */
    @TableField(value = "score")
    private Integer score;

    /**
     * 主表id
     */
    @TableField(value = "data_id")
    private Integer dataId;

    /**
     * 队伍在系列赛中的顺序
     */
    @TableField(value = "sequence")
    private Integer sequence;


}