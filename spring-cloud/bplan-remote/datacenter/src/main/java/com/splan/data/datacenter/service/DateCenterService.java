package com.splan.data.datacenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.data.datacenter.bean.BetOrderBean;
import com.splan.base.bean.BetTopicsBean;
import com.splan.data.datacenter.bean.MoneyRecordBean;
import com.splan.base.http.CommonResult;
import com.splan.data.datacenter.dto.DateDto;
import com.splan.data.datacenter.dto.DateDtoOrder;
import com.splan.data.datacenter.dto.DateDtoUpdate;

public interface DateCenterService {
    CommonResult<IPage<BetTopicsBean>> betData(Page page, String bigBusiness, String smallBusiness, String gameId, DateDto dateDto, String dataId, String betId, String betStatus);

    CommonResult<IPage<BetOrderBean>>  orderData(Page page, DateDto dateDto, String orderStatus, String searchId, String orderId, String orderType, DateDtoOrder dateDtoOrder, DateDtoUpdate dateDtoUpdate);

    CommonResult<IPage<MoneyRecordBean>> moneyRecord(Page page, String searchId, String orderId, DateDto dateDto);
}
