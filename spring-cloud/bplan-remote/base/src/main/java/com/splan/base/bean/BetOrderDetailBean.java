package com.splan.base.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.splan.base.enums.OrderStatus;
import com.splan.base.enums.WinLoseStatus;
import com.splan.base.result.GameResult;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * bet_order_detail
 */
@Data
@TableName(value = "bet_order_detail")
public class BetOrderDetailBean extends BaseLongBean implements Serializable {

    /**
     * 
     */
    @TableField(value = "data_id")
    private Integer dataId;

    /**
     * 
     */
    @TableField(value = "bet_order_id")
    private Long betOrderId;

    /**
     * 
     */
    @TableField(value = "bet_option_id")
    private Integer betOptionId;

    /**
     * notopen,win,lose
     */
    @TableField(value = "win_lose")
    private WinLoseStatus winLose;

    /**
     * 失败，未结算，已结算FAIL,UNSETTLED,SETTLED
     */
    @TableField(value = "status")
    private OrderStatus status;

    /**
     * 
     */
    @TableField(value = "odd")
    private BigDecimal odd;

    /**
     * 是否串单
     */
    @TableField(value = "order_type")
    private Boolean orderType;

    //@TableField(value = "extra")
    @TableField(exist = false)
    private String extra;

    /**
     * 取消原因
     */
    @TableField(value = "cancel_reason")
    private String cancelReason;

    /*@TableField(value = "error_msg")
    private String errorMsg;*/

    @TableField(exist = false)
    private Integer gameDataId;

    @TableField(exist = false)
    private String groupName;

    @TableField(exist = false)
    private Integer sequence;

    @TableField(exist = false)
    private Double markValue;

    @TableField(value = "game_id")
    private Integer gameId;

    @TableField(exist = false)
    private String logo;

    @TableField(exist = false)
    private String topicableName;

    @TableField(exist = false)
    private boolean rollingBall;

    @TableField(value = "vs_detail")
    private String vsDetail;

    @TableField(value = "vs")
    private String vs;

    @TableField(value = "check_time")
    private Date checkTime;

    @TableField(value = "rollback_ime")
    private Date rollbackTime;

    @TableField(exist = false)
    private List<GameResult> gameResultList;

    @TableField(exist = false)
    private BetOrderBean betOrderBean;

    @TableField(exist = false)
    private List<BetTopicsBean> betTopicsBean;

    @TableField(exist = false)
    private Integer GameNo;

    @TableField(exist = false)
    private Integer betResult;

    //xBet
    @TableField(exist = false,value = "name_en")
    private String nameEn;

    @TableField(exist = false,value = "betId")
    private String betId;

}