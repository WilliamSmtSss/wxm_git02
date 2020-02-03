package com.splan.xbet.admin.back.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.splan.base.bean.BetOptionBean;
import com.splan.xbet.admin.back.mappers.BetOptionBeanMapper;
import com.splan.xbet.admin.back.service.IBetOptionService;
import org.springframework.stereotype.Service;

@Service
public class BetOptionServiceImpl extends ServiceImpl<BetOptionBeanMapper, BetOptionBean> implements IBetOptionService {

    @Override
    public String getVsDetail(Integer betDataId) {
        return getBaseMapper().getVsDetail(betDataId);
    }
}
