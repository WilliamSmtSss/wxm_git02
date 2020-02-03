package com.splan.bplan.thread;

import com.splan.base.bean.BetOrderBean;
import com.splan.base.bean.UserBalanceBean;
import com.splan.base.bean.UserBean;
import com.splan.bplan.dto.BetCameoOrderDto;
import com.splan.bplan.service.*;

import java.math.BigDecimal;

/**
 * 异步下单
 */
public class OrderCameoOutRunnable implements Runnable {

    private BetCameoOrderDto betOrderDto;
    private BetOrderBean betOrderBean;
    private UserBalanceBean userBalanceBean;
    private BigDecimal amount;
    private BigDecimal from;
    private UserBean userBean;

    private IMoneyRecordService moneyRecordService;

    private IActiveService activeService;

    private INoticeService noticeService;

    private IBetOutService betService;

    public OrderCameoOutRunnable(UserBean userBean,IBetOutService betService, IMoneyRecordService moneyRecordService, IActiveService activeService,
                                 INoticeService noticeService, BetCameoOrderDto betOrderDto, BetOrderBean betOrderBean, UserBalanceBean userBalanceBean, BigDecimal amount, BigDecimal from){
        this.betOrderDto = betOrderDto;
        this.betOrderBean = betOrderBean;
        this.userBalanceBean = userBalanceBean;
        this.amount = amount;
        this.from = from;
        this.betService = betService;
        this.moneyRecordService = moneyRecordService;
        this.activeService = activeService;
        this.noticeService = noticeService;
        this.userBean = userBean;
    }
    @Override
    public void run(){
        this.betService.synSendCameoOrder(userBean,betOrderDto,betOrderBean,userBalanceBean,amount,from);
    }
}
