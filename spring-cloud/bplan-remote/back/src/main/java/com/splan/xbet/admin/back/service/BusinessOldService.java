package com.splan.xbet.admin.back.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.BetOrderBean;
import com.splan.base.bean.BetOrderDetailBean;
import com.splan.base.bean.GameTypeBean;
import com.splan.base.bean.UserBean;
import com.splan.xbet.admin.back.dto.DateDto;
import com.splan.xbet.admin.back.dto.ScreenForBetOrderDto;
import com.splan.base.enums.orderenums.*;
import com.splan.xbet.admin.back.result.BetResult;

import java.util.List;

public interface BusinessOldService {
    JSONArray revenueList(DateDto dateDto, String apiid);

    IPage<BetOrderBean> getBetOrderPage(Long betId, Long userId, Long option, Page page, BetDetailOrderBy betDetailOrderBy, OrderByComm orderByComm);

    List<BetOrderDetailBean> getBetDetail(Long betOrderId);

    IPage<BetResult> getBetResult(Integer gameTypeId, DateDto dateDto, String status, IPage<BetResult> page, BetOrderBy betOrderBy, OrderByComm orderType, String hasOrder);
//    IPage<BetResult> getBetResult(Integer gameTypeId, DateDto dateDto, String status, IPage<BetResult> page);

    IPage<UserBean> getUserList(String userId, IPage<UserBean> page, String apiid);

    IPage<BetOrderBean> getUserOrderList(Long userId, Integer gameTypeId, DateDto dateDto, IPage page, UserOrderOrderBy userOrderOrderBy, OrderByComm orderByComm);

    IPage<BetOrderBean> getOrderPage(String userId, Page page, ScreenForBetOrderDto screenForBetOrderDto, String apiid, OrderOrderBy orderOrderBy, OrderByComm orderByComm);

    List<GameTypeBean> gameTypes();
}
