package com.splan.bplan.service.impl;

import com.splan.base.bean.CommonPayParamBean;
import com.splan.base.bean.UserBean;
import com.splan.bplan.dto.PayDepositDto;
import com.splan.bplan.dto.PayWithDrawDto;
import com.splan.base.enums.OperationResult;
import com.splan.bplan.http.CommonResult;
import com.splan.bplan.http.DataResult;
import com.splan.bplan.service.IPayService;
import com.splan.bplan.utils.OKHttpUtil;
import com.splan.bplan.utils.ResultUtil;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class PayServiceImpl implements IPayService {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private static BeanFactory beanFactory;

    @Override
    public IPayService getInstance(String type) {
        switch (type) {
            case "kalipay": return applicationContext.getBean(KaliPayServiceImpl.class);
            case "jafupay": return applicationContext.getBean(JafuPayServiceImpl.class);
            case "hypay": return applicationContext.getBean(HyPayServiceImpl.class);
            default: return this;
        }
    }

    @Override
    public String deposit(UserBean user, CommonPayParamBean payParamBean, PayDepositDto payDeposit, HttpServletRequest request) {
        throw new UnsupportedOperationException();
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
        return "payService";
    }

    public static void main(String[] args) {
        Map<String, String> data = new HashMap<>();
        data.put("amount", "10.00");
        data.put("club", "1");
        data.put("currency", "CNY");
        data.put("ip", "127.0.0.1");
        data.put("league", "1");
        data.put("login_name", "test");
        data.put("channel", "AliPay");
        data.put("callback_url", "http://47.111.19.62:9002/api/pay/result");
        data.put("payer", "test");
        data.put("payment_reference", "7");
        data.put("product_id", "137");
        data.put("sign", "E1A92FACE73E3D3B41260C8FB9C53DD7");
        DataResult req = OKHttpUtil.httpPostByUrlencoded("http://officeapi.telfapay.com/online-pay/init-deposit-adapter", data);
        System.out.println(req);
    }
}
