package com.splan.bplan.thread;

import com.splan.bplan.service.IActiveService;

import java.util.concurrent.Callable;

public class OrderActiveCallable implements Callable<Boolean> {
    private IActiveService activeService;

    private Long userId;


    public OrderActiveCallable(IActiveService activeService,Long userId){
        this.activeService = activeService;
        this.userId = userId;
    }

    @Override
    public Boolean call() throws Exception {
        return activeService.afterOrder(this.userId);
    }
}
