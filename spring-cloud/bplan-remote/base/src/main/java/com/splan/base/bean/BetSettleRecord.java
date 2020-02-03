package com.splan.base.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.splan.base.enums.OrderStatus;
import com.splan.base.enums.WinLoseStatus;
import lombok.Data;

/**
 * bet_settle_record
 */
@TableName(value = "bet_settle_record")
@Data
public class BetSettleRecord extends BaseLongBean {


    /**
     * 
     */
    @TableField(value = "bet_data_id")
    private Integer betDataId;

    /**
     *
     */
    @TableField(value = "bet_option_id")
    private Integer betOptionId;

    /**
     * 
     */
    @TableField(value = "win_lose")
    private WinLoseStatus winLose;

    /**
     * 
     */
    @TableField(value = "status")
    private OrderStatus status;

    /**
     * 结算数量
     */
    @TableField(value = "settle_count")
    private Integer settleCount;


}