package com.splan.base.bean.ash;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "ash_game")
public class AshGamesBean extends AshBaseBean implements AshBean {

    @TableField(value = "name")
    private String name;

    @TableField(value = "short_name")
    private String shortName;

    @TableField(value = "code")
    private String code;


}
