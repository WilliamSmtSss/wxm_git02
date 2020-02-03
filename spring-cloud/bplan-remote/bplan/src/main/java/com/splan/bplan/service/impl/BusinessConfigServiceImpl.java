package com.splan.bplan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.splan.base.bean.BusinessConfigBean;
import com.splan.bplan.mappers.BusinessConfigFrontMapper;
import com.splan.bplan.result.ConfigResult;
import com.splan.bplan.service.IBusinessConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessConfigServiceImpl extends ServiceImpl<BusinessConfigFrontMapper, BusinessConfigBean> implements IBusinessConfigService {

    @Autowired
    private BusinessConfigFrontMapper businessConfigFrontMapper;

    @Override
    public BusinessConfigBean selectByApiId(String apiId) {
        return businessConfigFrontMapper.selectByApiId(apiId);
    }

    @Override
    public ConfigResult getByApiId(String apiId) {
        BusinessConfigBean businessConfigBean = selectByApiId(apiId);
        ConfigResult configResult = new ConfigResult();
        if (businessConfigBean==null){
            return configResult;
        }
        configResult.setLogo(businessConfigBean.getLogo());
        configResult.setHasHeader(1);
        return configResult;
    }
}
