package com.splan.bplan.thread;

import com.splan.base.bean.BetOptionBean;
import com.splan.bplan.service.IBetOrderService;
import com.splan.bplan.service.INoticeService;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * 盘口结算
 */
public class BetFinishCallable implements Runnable {

    private IBetOrderService betOrderService;

    private List<BetOptionBean> betOptionBean;

    private INoticeService noticeService;

    public BetFinishCallable(List<BetOptionBean> betOptionBean,IBetOrderService betOrderService,INoticeService noticeService){
        this.betOptionBean = betOptionBean;
        this.betOrderService = betOrderService;
        this.noticeService = noticeService;
    }

    @Override
    public void run(){
        betOrderService.checkResult(betOptionBean);
    }
}
