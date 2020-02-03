package com.splan.xbet.admin.back.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.BetOrderBean;
import com.splan.base.bean.BetTopicsBean;
import com.splan.base.http.CommonResult;
import com.splan.xbet.admin.back.dto.DateDto;

public interface XBetDataService {

    CommonResult<IPage<BetTopicsBean>> betData(Page page, String bigBusiness, String smallBusiness, String gameId, DateDto dateDto, String dataId, String betId, String betStatus, String export);

    CommonResult<IPage<BetTopicsBean>> betDataExport(Page page,String bigBusiness, String smallBusiness, String gameId, DateDto dateDto, String dataId, String betId,String betStatus,String export);

    CommonResult<IPage<BetOrderBean>>  orderData(Page page, String bigBusiness, String smallBusiness, String gameId, DateDto dateDto, String orderStatus, String searchId, String orderId, String betId,String orderType, String export,String dataId);

    CommonResult<IPage<BetOrderBean>>  orderDataExport(Page page, String bigBusiness, String smallBusiness, String gameId, DateDto dateDto, String orderStatus, String searchId, String orderId, String betId,String orderType, String export,String dataId);

}
