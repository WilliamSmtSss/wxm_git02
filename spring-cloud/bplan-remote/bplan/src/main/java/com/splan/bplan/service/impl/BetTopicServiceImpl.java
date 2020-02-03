package com.splan.bplan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.splan.base.bean.BetTopicsBean;
import com.splan.bplan.mappers.BetTopicsBeanMapper;
import com.splan.bplan.service.IBetTopicService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BetTopicServiceImpl extends ServiceImpl<BetTopicsBeanMapper, BetTopicsBean> implements IBetTopicService{

    public List<BetTopicsBean> selectAllByDataIdAndNumber(Integer dataId, Integer number){
        return getBaseMapper().selectAllByDataIdAndNumber(dataId,number);
    }
}
