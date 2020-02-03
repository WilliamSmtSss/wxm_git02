package com.splan.bplan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.splan.base.bean.BetOptionBean;
import com.splan.bplan.mappers.BetOptionBeanMapper;
import com.splan.bplan.service.IBetOptionService;
import org.springframework.stereotype.Service;

@Service
public class BetOptionServiceImpl extends ServiceImpl<BetOptionBeanMapper, BetOptionBean> implements IBetOptionService {

    @Override
    public String getVsDetail(Integer betDataId) {
        return getBaseMapper().getVsDetail(betDataId);
    }
}
