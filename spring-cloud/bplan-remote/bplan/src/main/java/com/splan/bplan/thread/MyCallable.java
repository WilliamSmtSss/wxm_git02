package com.splan.bplan.thread;

import com.splan.base.bean.UserBean;
import com.splan.bplan.dto.OrderBetDto;
import com.splan.bplan.service.IBetService;
import com.splan.bplan.utils.JsonUtil;

import java.util.concurrent.atomic.AtomicInteger;

public class MyCallable implements Runnable {

    private IBetService betService;


    private UserBean userBean;

    private AtomicInteger x;

    public MyCallable(IBetService betService,UserBean userBean,AtomicInteger integer){
        this.betService = betService;
        this.userBean = userBean;
        this.x = integer;
    }



    @Override
    public void run() {
        String data = "{\"orders\":[{\"bet_option_id\": \"59\",\"amount\":\"5\", \"odd\":\"1.49\"}],\"confirm\":true}";
        OrderBetDto orderBetDto = JsonUtil.stringToObject(data, OrderBetDto.class);
        betService.order(userBean,orderBetDto);
        System.out.println(userBean.getId()+"-----"+x.getAndIncrement());
    }
}
