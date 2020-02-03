package com.splan.bplan.thread;

import com.splan.base.bean.BetOrderBean;
import com.splan.base.bean.UserBalanceBean;
import com.splan.base.bean.UserBean;
import com.splan.bplan.dto.BetOrderDto;
import com.splan.bplan.service.*;

import java.math.BigDecimal;

/**
 * 异步下单
 */
public class OrderOutRunnable implements Runnable {

    private BetOrderDto betOrderDto;
    private BetOrderBean betOrderBean;
    private UserBalanceBean userBalanceBean;
    private BigDecimal amount;
    private BigDecimal from;
    private UserBean userBean;

    private IMoneyRecordService moneyRecordService;

    private IActiveService activeService;

    private INoticeService noticeService;

    private IBetOutService betOutService;

    public OrderOutRunnable(UserBean userBean,IBetOutService betOutService, IMoneyRecordService moneyRecordService, IActiveService activeService,
                            INoticeService noticeService, BetOrderDto betOrderDto, BetOrderBean betOrderBean, UserBalanceBean userBalanceBean, BigDecimal amount, BigDecimal from){
        this.betOrderDto = betOrderDto;
        this.betOrderBean = betOrderBean;
        this.userBalanceBean = userBalanceBean;
        this.amount = amount;
        this.from = from;
        this.betOutService = betOutService;
        this.moneyRecordService = moneyRecordService;
        this.activeService = activeService;
        this.noticeService = noticeService;
        this.userBean = userBean;
    }

    @Override
    public void run() {
        this.betOutService.asynSendOrder(userBean,betOrderDto,betOrderBean,userBalanceBean,amount,from);
    }
}
