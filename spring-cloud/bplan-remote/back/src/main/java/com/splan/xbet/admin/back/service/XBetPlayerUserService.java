package com.splan.xbet.admin.back.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.MoneyRecordBean;
import com.splan.base.bean.UserBean;
import com.splan.base.http.CommonResult;

public interface XBetPlayerUserService {

    CommonResult<IPage<UserBean>> list(Page page, String bigBusiness, String smallBusiness, String searchId, String export);

    CommonResult<IPage<UserBean>> listExport(Page page, String bigBusiness, String smallBusiness, String searchId,String export);

    CommonResult<IPage<MoneyRecordBean>> moneyRecord(Page page,String bigBusiness, String smallBusiness, String searchId, String orderId,String export);

    CommonResult<IPage<MoneyRecordBean>> moneyRecordExport(Page page, String bigBusiness, String smallBusiness, String searchId, String orderId, String export);

}
