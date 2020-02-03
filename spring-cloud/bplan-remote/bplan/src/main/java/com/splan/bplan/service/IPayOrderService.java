package com.splan.bplan.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.splan.base.bean.PayOrderBean;
import com.splan.base.enums.AccessType;
import com.splan.base.enums.Level;
import com.splan.base.enums.OperationResult;
import com.splan.base.enums.OrderStatus;
import com.splan.bplan.http.CommonResult;
import com.splan.bplan.result.OperateResult;
import com.splan.bplan.result.PayOrderResult;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IPayOrderService extends IService<PayOrderBean> {
    CommonResult<String> updateOrder(String orderNo, String status, BigDecimal amount);
    JSONObject statisticsDeposit();
    IPage<PayOrderResult> selectPage(IPage<PayOrderResult> page, Date startDate, Date endDate, AccessType accessType, OrderStatus orderStatus);
    JSONObject statisticsWithdraw();
    JSONObject statisticsWithdraw(OperationResult... operationResults);
    IPage<PayOrderResult> selectPage(IPage<PayOrderResult> page, Date startDate, Date endDate, AccessType accessType,
                                     OperationResult... operationResults);
    IPage<PayOrderResult> selectAgentPage(IPage<PayOrderResult> page, Date startDate, Date endDate, AccessType accessType,
                                     OperationResult... operationResults);

    IPage<OperateResult> operateList(IPage<OperateResult> page);

    BigDecimal getTodayamountByUserType(List<String> types);

    JSONObject queryWitdraw(String userType,String orderType);
}
