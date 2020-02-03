package com.splan.auth.rest;

import com.splan.auth.dao.mapper.ProxyConfigMapper;
import com.splan.auth.service.ProxyConfigService;
import com.splan.base.bean.ProxyConfig;
import com.splan.base.service.ProxyConfigProvideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/proxy")
public class ProxyResource implements ProxyConfigProvideService {

    @Autowired
    private ProxyConfigService proxyConfigService;

    @Autowired
    private ProxyConfigMapper proxyConfigMapper;

    
    /*@GetMapping("/get")
    public ProxyConfig getClient(String clientId) {
        ProxyConfig proxyConfig = proxyConfigService.selectProxyConfigByClientId(clientId);
        return proxyConfig;
    }*/

    @Override
    @GetMapping("/get")
    public ProxyConfig selectProxyConfigByClientId(String clientId) {
        ProxyConfig proxyConfig = proxyConfigService.selectProxyConfigByClientId(clientId);
        return proxyConfig;
    }

    @Override
    @GetMapping("/save")
    public int save(String clientId) {
        return proxyConfigMapper.save(clientId);
    }

}
