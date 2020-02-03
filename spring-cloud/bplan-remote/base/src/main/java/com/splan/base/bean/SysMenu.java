package com.splan.base.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "sys_menu")
public class SysMenu extends BaseBean implements Serializable {
    @TableField(value = "menu_code")
    public String menuCode;

    @TableField(value = "menu_name")
    public String menuName;

    @TableField(value = "status")
    public Boolean status;
}
