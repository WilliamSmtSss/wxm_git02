package com.splan.base.bean.ash;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "ash_game_league")
public class AshGameLeagueBean extends AshBaseBean implements AshBean {

    @TableField(value = "name")
    private String name;

    @TableField(value = "short_name")
    private String shortName;

    @TableField(value = "bonus")
    private Long bonus;

    @TableField(value = "bonus_type")
    private Integer bonusType;//奖金单位

    @TableField(value = "alias")
    private String alias;//联赛别名

    @TableField(value = "logo")
    private String logo;//logo

    @TableField(value = "organizer")
    private String organizer;//组织者

    @TableField(value = "level")
    private Integer level;//联赛等级

    @TableField(value = "local")
    private String local;//地区

    @TableField(value = "game_id")
    private Long gameId;

    @TableField(value = "area_id")
    private Long areaId;

    @TableField(value = "game_type")
    private Integer gameType;

    @TableField(value = "limit_team")
    private Integer limitTeam;

    @TableField(value = "description")
    private String description;


}
