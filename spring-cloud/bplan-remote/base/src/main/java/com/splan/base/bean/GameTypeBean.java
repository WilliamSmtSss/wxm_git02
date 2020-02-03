package com.splan.base.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * game_type
 */
@Data
@TableName(value = "game_type")
public class GameTypeBean extends BaseBean {
    /**
     * 
     */
    @TableField(value = "name_en")
    private String nameEn;

    /**
     * 
     */
    @TableField(value = "name_ch")
    private String nameCh;

    /**
     * 
     */
    @TableField(value = "logo")
    private String logo;

    /**
     * 
     */
    @TableField(value = "status")
    private String status;

    /**
     *
     */
    @TableField(value = "selected_logo")
    private String selectedLogo;

    @TableField(exist = false)
    private Integer gameCount;


}