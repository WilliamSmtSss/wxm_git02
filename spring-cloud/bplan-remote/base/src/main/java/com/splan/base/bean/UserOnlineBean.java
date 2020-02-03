package com.splan.base.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("user_online")
@Data
public class UserOnlineBean extends  BaseLongBean {
//    @TableId(value = "id",type = IdType.AUTO)
//    private Integer id;
    @TableField(value = "online")
    private Integer online;
//    @TableField(value = "createtime",fill = FieldFill.INSERT)
//    private Date createtime;
//    @TableField(value = "updatetime",fill = FieldFill.INSERT_UPDATE)
//    private Date updatetime;

}
