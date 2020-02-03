package com.splan.base.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "auth")
public interface OauthClientDetailsService {

    @RequestMapping(method = RequestMethod.GET, value = "/saveClient")
    int saveClient(@RequestParam("clientId") String clientId, @RequestParam("clientSecret") String clientSecret,@RequestParam("ipWhitelist") String ipWhitelist);

    @RequestMapping(method = RequestMethod.GET, value = "/updateClient")
    int updateClient(@RequestParam("clientId") String clientId,@RequestParam("clientSecret") String clientSecret,@RequestParam("ipWhitelist") String ipWhitelist);

    @RequestMapping(method = RequestMethod.GET, value = "/getClient")
    Map<String,Object> getClient2(@RequestParam("clientId") String clientId);

}
