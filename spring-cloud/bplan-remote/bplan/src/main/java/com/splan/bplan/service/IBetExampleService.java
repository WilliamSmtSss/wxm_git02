package com.splan.bplan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.splan.base.bean.BetExampleBean;

public interface IBetExampleService extends IService<BetExampleBean> {

    int cacheBetExample();

    int cacheParam();

    BetExampleBean selectBySupport(String topicableType,Integer category,Integer support,Integer gameType);
}
