package com.splan.xbet.frontback.frontback.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.BetOrderBean;
import com.splan.base.bean.UserBean;
import com.splan.base.http.CommonResult;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public interface DataManagerService {

    CommonResult<Page<UserBean>> userList(@RequestBody Map<String,Object> requestParam);

    CommonResult<Page<BetOrderBean>> orderList(@RequestBody Map<String,Object> requestParam);

}




