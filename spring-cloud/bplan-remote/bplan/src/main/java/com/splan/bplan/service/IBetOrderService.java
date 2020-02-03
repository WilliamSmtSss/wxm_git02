package com.splan.bplan.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.splan.base.bean.BetOptionBean;
import com.splan.base.bean.BetOrderBean;
import com.splan.base.bean.BetTopicsBean;
import com.splan.base.bean.UserBean;
import com.splan.bplan.http.CommonResult;
import com.splan.bplan.result.BetOrderResult;

import java.math.BigDecimal;
import java.util.List;
public interface IBetOrderService extends IService<BetOrderBean> {

    CommonResult<IPage<BetOrderBean>> selectByType(UserBean userBean, Integer type, String orderDate, Page page);

    Integer checkResult(List<BetOptionBean> betOptionBean);

    Integer checkHistoryResult();

    Integer syncToOrder();

    CommonResult<List<BetOrderResult>> rollOrderList();

    /** -- -- **/
    CommonResult<IPage<BetOrderBean>> selectAll(JSONObject jsonObject, Page page);

    BigDecimal getTodayOrderAmount();

    BigDecimal getYestTodayOrderAmount();

    BigDecimal getTodayProfitAmount();

    BigDecimal getYestTodayProfitAmount();

    Integer cancelOrders(String[] orders);

    Integer recheckResult(BetTopicsBean betTopicsBean);
}
