package com.splan.base.service.v2;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.BusinessCurrencyConfigBean;
import com.splan.base.bean.SysUser;
import com.splan.base.http.CommonResult;
import com.splan.base.param.remote.BusinessFormsParam;
import com.splan.base.param.remote.FirstDataParam;
import com.splan.base.result.BusinessFormsResult;
import com.splan.base.result.remote.FrontDataResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(value = "back")
public interface V2BackService {

    @RequestMapping(method = RequestMethod.POST, value = "/front/remote/businessForms")
    String businessForms(@RequestBody BusinessFormsParam businessFormsParam);

    @RequestMapping(method = RequestMethod.POST, value = "/front/remote/firstData")
    String firstData(@RequestBody FirstDataParam firstDataParam);

    //商户
    @RequestMapping(method = RequestMethod.POST, value = "/front/remote/busSel")
    String busSel(@RequestBody Map<String,Object> requestParam);

    @RequestMapping(method = RequestMethod.POST, value = "/front/remote/busEdit")
    void busEdit(@RequestBody Map<String,Object> requestParam);

    @RequestMapping(method = RequestMethod.POST, value = "/front/remote/busAdd")
    void busAdd(@RequestBody Map<String,Object> requestParam);

    @RequestMapping(method = RequestMethod.POST, value = "/front/remote/CurrencySel")
    String CurrencySel(@RequestBody Map<String,Object> requestParam);

    //商户，后台
        //用户
    @RequestMapping(method = RequestMethod.POST, value = "/front/remote/userManager")
    String userManager(@RequestBody Map<String,Object> requestParam);
        //注单
    @RequestMapping(method = RequestMethod.POST, value = "/front/remote/orderManager")
    String orderManager(@RequestBody Map<String,Object> requestParam);
        //游戏列表
    @RequestMapping(method = RequestMethod.POST, value = "/front/remote/gameList")
    String gameList(@RequestBody Map<String,Object> requestParam);

}
