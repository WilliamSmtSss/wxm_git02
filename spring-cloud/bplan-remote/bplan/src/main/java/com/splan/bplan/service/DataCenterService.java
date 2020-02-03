package com.splan.bplan.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.BetOrderBean;
import com.splan.base.bean.PayOrderBean;
import com.splan.base.bean.UserBalanceBean;
import com.splan.base.bean.UserOnlineBean;

import java.util.Date;

public interface DataCenterService {

    JSONObject getRegisterInfo(JSONObject jsonObject);

    JSONObject getDepositInfo();

    IPage<PayOrderBean> getDepositList(Page page, Date startDate, Date endDate);

    JSONObject getWithdrawInfo();

    IPage<PayOrderBean> getWithdrawList(Page page, Date startDate, Date endDate);

    JSONObject getBalanceInfo();

    IPage<UserBalanceBean> getBalanceList(Page page, Date startDate, Date endDate);

    JSONObject getOrderInfo();

    IPage<BetOrderBean> getOrderList(Page page, Date startDate, Date endDate);

    JSONObject getProfitInfo();

    IPage<BetOrderBean> getProfitList(Page page, Date startDate, Date endDate);

    JSONObject getOnlineInfo();

    IPage<UserOnlineBean> getOnlineList(Page page, Date startDate, Date endDate);
}
