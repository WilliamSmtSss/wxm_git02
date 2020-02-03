package com.splan.xbet.admin.back.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.splan.base.bean.BetOrderDetailBean;
import com.splan.xbet.admin.back.mappers.BetOrderDetailBeanMapper;
import com.splan.xbet.admin.back.service.IBetOrderDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BetOrderDetailServiceImpl extends ServiceImpl<BetOrderDetailBeanMapper, BetOrderDetailBean> implements IBetOrderDetailService {
    @Override
    public List<BetOrderDetailBean> selectByDataId(Integer dataId) {
        return getBaseMapper().selectByDataId(dataId);
    }
}
