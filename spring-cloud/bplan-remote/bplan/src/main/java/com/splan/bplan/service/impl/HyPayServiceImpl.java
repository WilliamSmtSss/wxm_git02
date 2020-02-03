package com.splan.bplan.service.impl;

import com.splan.base.bean.CommonPayParamBean;
import com.splan.base.bean.UserBean;
import com.splan.bplan.dto.PayDepositDto;
import com.splan.bplan.dto.PayWithDrawDto;
import com.splan.base.enums.HyPayType;
import com.splan.base.enums.OperationResult;
import com.splan.bplan.http.CommonResult;
import com.splan.bplan.http.DataResult;
import com.splan.bplan.http.pay.HyPayReqBody;
import com.splan.bplan.service.IPayService;
import com.splan.bplan.utils.IpUtil;
import com.splan.bplan.utils.OKHttpUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

public class HyPayServiceImpl implements IPayService {

    private String depositUrl = "Pay/App/ApplyPay";

    @Override
    public IPayService getInstance(String type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String deposit(UserBean user, CommonPayParamBean payParamBean, PayDepositDto payDeposit, HttpServletRequest request) {
        String ip = IpUtil.getIpAddr(request);
        HyPayReqBody hyPayReqBody = new HyPayReqBody();
        HyPayReqBody.BizContent bizContent = hyPayReqBody.getBizContent();
        bizContent.setAttach(payDeposit.getAttach());
        bizContent.setTotalFee(payDeposit.getAmount());
        bizContent.setClientIp(ip);
        bizContent.setOutTradeNo("cs" + Math.random());
        bizContent.setNofityUrl(payParamBean.getCallbackUrl());
        bizContent.setReturnUrl(payParamBean.getHrefbackUrl());
        bizContent.setSubject("账户充值");
        bizContent.setChannelType(HyPayType.valueOf(payDeposit.getPayType()).getChannel());
        hyPayReqBody.setBizContent(bizContent);
        hyPayReqBody.setKey(payParamBean.getApiKey());
        hyPayReqBody.setAppId(payParamBean.getApiKey());
        hyPayReqBody.setTimestamp(new Date());
        Map<String, String> data = hyPayReqBody.getData();
        DataResult dataResult = OKHttpUtil.httpPostByUrlencoded(payParamBean.getApiUrl() + depositUrl, data);
        return dataResult.getResult();
    }

    @Override
    public CommonResult<String> withdraw(CommonPayParamBean payParamBean, PayWithDrawDto payWithDrawDto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public CommonResult<String> review(CommonPayParamBean payParamBean, Long orderId, OperationResult operate) {
        return null;
    }

    @Override
    public String getPayType() {
        return "hypay";
    }
}
