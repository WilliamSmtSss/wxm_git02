package com.splan.bplan.config;

import com.splan.bplan.service.IBetExampleService;
import com.splan.bplan.service.IGiftListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class MyApplicationRunner implements ApplicationRunner {

    @Autowired
    private IBetExampleService betExampleService;

    @Autowired
    private IGiftListService giftListService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        betExampleService.cacheBetExample();
        betExampleService.cacheParam();//缓存系统
        giftListService.cacheList();
    }
}
