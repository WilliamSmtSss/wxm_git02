package com.splan.base.bean;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "x_export")
public class XExportBean extends BaseBean{

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected Date createTime;

    @TableField(value = "sys_id")
    private Integer sysId;

    @TableField(value = "file_name")
    private String fileName;

    @TableField(value = "url")
    private String url;

    @TableField(value = "sys_name",exist = false)
    private String sysName;

}
