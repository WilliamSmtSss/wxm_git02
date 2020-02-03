package com.splan.bplan.service;

import com.splan.base.bean.CommonPayParamBean;
import com.splan.base.bean.UserBean;
import com.splan.bplan.dto.PayDepositDto;
import com.splan.bplan.dto.PayWithDrawDto;
import com.splan.base.enums.OperationResult;
import com.splan.bplan.http.CommonResult;

import javax.servlet.http.HttpServletRequest;

public interface IPayService {
    IPayService getInstance(String type);

    String deposit(UserBean user, CommonPayParamBean payParamBean, PayDepositDto payDeposit, HttpServletRequest request);

    CommonResult<String> withdraw(CommonPayParamBean payParamBean, PayWithDrawDto payWithDrawDto);

    CommonResult<String> review(CommonPayParamBean payParamBean, Long orderId, OperationResult operate);

    String getPayType();
}
