package com.splan.base.service.v2;

import com.splan.base.param.backremote.BackProductOrderParam;
import com.splan.base.param.backremote.BackProductTrialParam;
import com.splan.base.param.remote.BusinessFormsParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(value = "frontback")
public interface FrontBackService {
    //服务管理
        //暂无
    //服务试用
        //新增
    @RequestMapping(method = RequestMethod.POST, value = "/frontback/remote/addTrial")
    String addTrial(@RequestBody BackProductTrialParam backProductTrialParam);
        //查询
    @RequestMapping(method = RequestMethod.POST, value = "/frontback/remote/selTrial")
    String selTrial(@RequestBody BackProductTrialParam backProductTrialParam);

    //服务申请
        //新增
    @RequestMapping(method = RequestMethod.POST, value = "/frontback/remote/addOrder")
    String addOrder(@RequestBody BackProductOrderParam backProductOrderParam);
        //查询
    @RequestMapping(method = RequestMethod.POST, value = "/frontback/remote/selOrder")
    String selOrder(@RequestBody BackProductOrderParam backProductOrderParam);

    //消息通知
    @RequestMapping(method = RequestMethod.POST, value = "/frontback/remote/addMessage")
    String addMessage(@RequestBody Map<String,Object> requestMap);

    //公告通知
    @RequestMapping(method = RequestMethod.POST, value = "/frontback/remote/selNotice")
    String selNotice(@RequestBody Map<String,Object> requestMap);

}
