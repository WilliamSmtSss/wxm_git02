package com.splan.bplan.thread;

import com.splan.base.enums.TriggerType;
import com.splan.bplan.service.IActiveService;

import java.util.concurrent.Callable;

public class ActiveCallable implements Callable<Boolean> {

    private IActiveService activeService;

    private Long userId;

    private Integer activeId;

    public ActiveCallable(IActiveService activeService,Long userId,Integer activeId){
        this.activeService = activeService;
        this.userId = userId;
        this.activeId = activeId;
    }

    @Override
    public Boolean call() throws Exception {
        return activeService.doActive(this.userId,this.activeId, TriggerType.BACK);
    }
}
