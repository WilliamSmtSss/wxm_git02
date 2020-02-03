package com.splan.bplan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.splan.base.bean.BetOrderDetailBean;
import com.splan.bplan.mappers.BetOrderDetailBeanMapper;
import com.splan.bplan.service.IBetOrderDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BetOrderDetailServiceImpl extends ServiceImpl<BetOrderDetailBeanMapper, BetOrderDetailBean> implements IBetOrderDetailService {
    @Override
    public List<BetOrderDetailBean> selectByDataId(Integer dataId) {
        return getBaseMapper().selectByDataId(dataId);
    }
}
