package com.splan.base.bean.ash;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public abstract class AshBaseBean {

    @TableId(value = "id",type = IdType.INPUT)
    protected Long id;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonIgnore
    protected Date createTime;

    @TableField(value = "update_time",update = "now()", fill = FieldFill.INSERT_UPDATE)
    @JsonIgnore
    protected Date updateTime;
}
