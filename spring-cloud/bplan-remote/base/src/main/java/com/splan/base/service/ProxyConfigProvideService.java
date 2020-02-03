package com.splan.base.service;

import com.splan.base.bean.ProxyConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "auth")
public interface ProxyConfigProvideService {

    @RequestMapping(method = RequestMethod.GET, value = "/proxy/get")
    ProxyConfig selectProxyConfigByClientId(@RequestParam("clientId") String clientId);

    @RequestMapping(method = RequestMethod.GET, value = "/proxy/save")
    int save(@RequestParam("clientId") String clientId);

}
