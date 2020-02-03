package com.splan.bplan.thread;

import com.splan.base.bean.BetTopicsBean;
import com.splan.bplan.service.IBetOrderService;
import com.splan.bplan.service.INoticeService;

/**
 * 盘口重新结算
 */
public class BetReFinishCallable implements Runnable {

    private IBetOrderService betOrderService;

    private BetTopicsBean betTopicsBean;

    private INoticeService noticeService;

    public BetReFinishCallable(BetTopicsBean betTopicsBean, IBetOrderService betOrderService, INoticeService noticeService){
        this.betTopicsBean = betTopicsBean;
        this.betOrderService = betOrderService;
        this.noticeService = noticeService;
    }

    @Override
    public void run(){
        betOrderService.recheckResult(betTopicsBean);
    }
}
