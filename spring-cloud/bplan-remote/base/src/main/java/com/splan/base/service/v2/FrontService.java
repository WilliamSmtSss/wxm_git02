package com.splan.base.service.v2;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(value = "backforbusiness")
public interface FrontService {

    @RequestMapping(method = RequestMethod.POST, value = "/frontRemote/checkCompanyInfo")
    String checkCompanyInfo(@RequestBody Map<String,Object> requestParam);

    @RequestMapping(method = RequestMethod.POST, value = "/frontRemote/selCompanyInfo")
    String selCompanyInfo(@RequestBody Map<String,Object> requestParam);

    @RequestMapping(method = RequestMethod.POST, value = "/frontRemote/busInfoSel")
    String busInfoSel(@RequestBody Map<String,Object> requestParam);

}
