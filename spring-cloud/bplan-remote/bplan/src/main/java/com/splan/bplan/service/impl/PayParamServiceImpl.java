package com.splan.bplan.service.impl;

import com.splan.base.bean.CommonPayParamBean;
import com.splan.bplan.mappers.CommonPayParamBeanMapper;
import com.splan.bplan.service.IPayParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayParamServiceImpl implements IPayParamService {

    @Autowired
    private CommonPayParamBeanMapper payParamBeanMapper;

    @Override
    public CommonPayParamBean getEnablePayParam() {
        List<CommonPayParamBean> list = payParamBeanMapper.selectEnableList();
        return list.size() > 0 ? list.get(0) : null;
    }
}
