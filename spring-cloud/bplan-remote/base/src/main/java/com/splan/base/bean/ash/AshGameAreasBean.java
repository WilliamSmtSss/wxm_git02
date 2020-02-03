package com.splan.base.bean.ash;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "ash_game_area")
public class AshGameAreasBean extends AshBaseBean implements AshBean {

    @TableField(value = "name")
    private String name;

    @TableField(value = "area")
    private String area;

    @TableField(value = "game_id")
    private Long gameId;

    @TableField(value = "description")
    private String description;


}
