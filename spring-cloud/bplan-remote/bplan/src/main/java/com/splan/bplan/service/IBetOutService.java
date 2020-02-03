package com.splan.bplan.service;

import com.splan.base.bean.BetOrderBean;
import com.splan.base.bean.UserBalanceBean;
import com.splan.base.bean.UserBean;
import com.splan.bplan.dto.BetCameoOrderDto;
import com.splan.bplan.dto.BetOrderDto;
import com.splan.bplan.dto.OrderBetDto;
import com.splan.bplan.http.CommonResult;

import java.math.BigDecimal;
import java.util.List;

/**
 * 外部平台下单
 */
public interface IBetOutService {

    /**
     * 普通下单
     * @param user
     * @param orderBetDto
     * @return
     */
    CommonResult<List<BetOrderBean>> order(UserBean user, OrderBetDto orderBetDto);

    /**
     * 串关下单
     * @param user
     * @param orderBetDto
     * @return
     */
    CommonResult<List<BetOrderBean>> cameoOrder(UserBean user, OrderBetDto orderBetDto);


    BetOrderBean asynSendOrder(UserBean user,BetOrderDto betOrderDto, BetOrderBean betOrderBean, UserBalanceBean userBalanceBean, BigDecimal amount, BigDecimal from);

    BetOrderBean synSendCameoOrder(UserBean user,BetCameoOrderDto betOrderDto, BetOrderBean betOrderBean, UserBalanceBean userBalanceBean, BigDecimal amount, BigDecimal from);

}
