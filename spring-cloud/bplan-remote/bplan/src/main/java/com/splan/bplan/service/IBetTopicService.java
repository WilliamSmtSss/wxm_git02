package com.splan.bplan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.splan.base.bean.BetTopicsBean;

import java.util.List;

public interface IBetTopicService extends IService<BetTopicsBean> {

    List<BetTopicsBean> selectAllByDataIdAndNumber(Integer dataId, Integer number);
}
