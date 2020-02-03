package com.splan.xbet.frontback.frontback.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.BetOrderBean;
import com.splan.base.bean.UserBean;
import com.splan.base.http.CommonResult;
import com.splan.base.service.v2.V2BackService;
import com.splan.base.utils.ResultUtil;
import com.splan.xbet.frontback.frontback.service.DataManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DataManagerServiceImpl implements DataManagerService {

    @Autowired
    private V2BackService v2BackService;

    @Override
    public CommonResult<Page<UserBean>> userList(Map<String, Object> requestParam) {
        if("1".equals(requestParam.get("dataType")))return ResultUtil.returnSuccess(null);
        Page<UserBean> page=null;
        try {
            page= JSON.parseObject(v2BackService.userManager(requestParam),new TypeReference<Page<UserBean>>(){});
        }catch (Exception e){

        }
        return ResultUtil.returnSuccess(page);
    }

    @Override
    public CommonResult<Page<BetOrderBean>> orderList(Map<String, Object> requestParam) {
        if("1".equals(requestParam.get("dataType")))return ResultUtil.returnSuccess(null);
        Page<BetOrderBean> page=null;
        try {
            page = JSON.parseObject(v2BackService.orderManager(requestParam), new TypeReference<Page<BetOrderBean>>(){});
        }catch (Exception e){

        }
        return ResultUtil.returnSuccess(page);
    }

}
