package com.splan.bplan.service.impl;

import com.splan.base.bean.CommonPayParamBean;
import com.splan.base.bean.UserBean;
import com.splan.bplan.dto.PayDepositDto;
import com.splan.bplan.dto.PayWithDrawDto;
import com.splan.base.enums.JafuPayType;
import com.splan.base.enums.OperationResult;
import com.splan.bplan.http.CommonResult;
import com.splan.bplan.http.pay.JafuPayReqBody;
import com.splan.bplan.service.IPayService;
import com.splan.bplan.utils.HttpUtil;
import com.splan.bplan.utils.IpUtil;
import com.splan.bplan.utils.ResultUtil;

import javax.servlet.http.HttpServletRequest;

public class JafuPayServiceImpl implements IPayService {

    private String depositUrl = "chargebank.aspx";

    @Override
    public IPayService getInstance(String type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String deposit(UserBean user, CommonPayParamBean payParamBean, PayDepositDto payDeposit, HttpServletRequest request) {
        String ip = IpUtil.getIpAddr(request);
        JafuPayReqBody jafuPayReqBody = new JafuPayReqBody();
        jafuPayReqBody.setPayerIp(ip);
        jafuPayReqBody.setParter(payParamBean.getApiId());
        jafuPayReqBody.setKey(payParamBean.getApiKey());
        jafuPayReqBody.setCallbackUrl(payParamBean.getCallbackUrl());
        jafuPayReqBody.setHrefbackUrl(payParamBean.getHrefbackUrl());
        jafuPayReqBody.setAttach(payDeposit.getAttach());
        jafuPayReqBody.setType(JafuPayType.valueOf(payDeposit.getPayType()).getCode());
        jafuPayReqBody.setValue(payDeposit.getAmount());
        jafuPayReqBody.setOrderId("2222");
        String apiUrl = payParamBean.getApiUrl() + depositUrl + "?" + jafuPayReqBody.getUrl();
        String response = HttpUtil.get(apiUrl);
        return "";
    }

    @Override
    public CommonResult<String> withdraw(CommonPayParamBean payParamBean, PayWithDrawDto payWithDrawDto) {
        return null;
    }

    @Override
    public CommonResult<String> review(CommonPayParamBean payParamBean, Long orderId, OperationResult operate) {
        return null;
    }

    @Override
    public String getPayType() {
        return "jafupay";
    }
}
