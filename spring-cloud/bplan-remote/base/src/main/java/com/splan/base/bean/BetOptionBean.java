package com.splan.base.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.splan.base.filter.CustomerFloatSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * bet_option
 */
@Data
@TableName(value = "bet_option")
public class BetOptionBean extends BaseBean implements Serializable {

    @TableId(value = "id",type = IdType.INPUT)
    private Integer id;

    /**
     * 
     */
    @TableField(value = "bet_data_id")
    private Integer betDataId;

    /**
     * 
     */
    @TableField(value = "odd")
    @JsonSerialize(using = CustomerFloatSerialize.class)
    private Float odd;

    /**
     * 
     */
    @TableField(value = "sequence")
    private Integer sequence;

    @TableField(value = "user_single_bet_profit_limit")
    private BigDecimal userSingleBetProfitLimit;

    @TableField(value = "name")
    private String name;

    /**
     * 0未开奖 1胜利 2失败
     */
    @TableField(value = "bet_result")
    @ApiModelProperty(value = "0未开奖 1胜利 2失败 3取消")
    private Integer betResult;


}