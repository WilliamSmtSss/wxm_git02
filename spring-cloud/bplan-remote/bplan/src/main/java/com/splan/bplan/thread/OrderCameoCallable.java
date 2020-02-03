package com.splan.bplan.thread;

import com.splan.base.bean.BetOrderBean;
import com.splan.base.bean.UserBalanceBean;
import com.splan.bplan.dto.BetCameoOrderDto;
import com.splan.bplan.dto.BetOrderDto;
import com.splan.bplan.service.IActiveService;
import com.splan.bplan.service.IBetService;
import com.splan.bplan.service.IMoneyRecordService;
import com.splan.bplan.service.INoticeService;

import java.math.BigDecimal;
import java.util.concurrent.Callable;

/**
 * 异步下单
 */
public class OrderCameoCallable implements Runnable {

    private BetCameoOrderDto betOrderDto;
    private BetOrderBean betOrderBean;
    private UserBalanceBean userBalanceBean;
    private BigDecimal amount;
    private BigDecimal from;

    private IMoneyRecordService moneyRecordService;

    private IActiveService activeService;

    private INoticeService noticeService;

    private IBetService betService;

    public OrderCameoCallable(IBetService betService, IMoneyRecordService moneyRecordService, IActiveService activeService,
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
    }
    @Override
    public void run(){
        this.betService.synSendCameoOrder(betOrderDto,betOrderBean,userBalanceBean,amount,from);
    }
}
