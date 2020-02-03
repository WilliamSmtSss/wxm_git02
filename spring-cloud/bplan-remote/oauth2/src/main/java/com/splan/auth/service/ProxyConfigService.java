package com.splan.auth.service;

import com.splan.auth.dao.mapper.ProxyConfigMapper;
import com.splan.base.bean.ProxyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProxyConfigService{

    @Autowired
    private ProxyConfigMapper proxyConfigMapper;

    public ProxyConfig selectProxyConfigByClientId(String clientId){
        return proxyConfigMapper.selectProxyConfigByClientId(clientId);
    }
}
