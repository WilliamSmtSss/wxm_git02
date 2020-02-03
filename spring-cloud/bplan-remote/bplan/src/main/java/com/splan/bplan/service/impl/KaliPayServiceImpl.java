package com.splan.bplan.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.splan.base.bean.*;
import com.splan.bplan.constants.Constants;
import com.splan.bplan.constants.SysParamConstants;
import com.splan.bplan.dto.PayDepositDto;
import com.splan.bplan.dto.PayWithDrawDto;
import com.splan.base.enums.*;
import com.splan.bplan.http.CommonResult;
import com.splan.bplan.http.DataResult;
import com.splan.bplan.http.pay.KaliPayReqBody;
import com.splan.bplan.mappers.*;
import com.splan.bplan.service.*;
import com.splan.bplan.utils.IpUtil;
import com.splan.bplan.utils.OKHttpUtil;
import com.splan.bplan.utils.OrderNoGeneratorUtil;
import com.splan.bplan.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Service
@Slf4j
public class KaliPayServiceImpl implements IPayService {

    private String depositUrl = "online-pay/init-deposit-adapter";

    private String withdrawUrl = "online-pay/payment/withdraw-adapter";

    @Autowired
    private UserCardBeanMapper userCardBeanMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PayOrderBeanMapper payOrderBeanMapper;

    @Autowired
    private UserBalanceBeanMapper userBalanceBeanMapper;

    @Autowired
    private SysParamBeanMapper sysParamBeanMapper;

    @Autowired
    private IPayOrderService payOrderService;

    @Override
    public IPayService getInstance(String type) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String deposit(UserBean user, CommonPayParamBean payParamBean, PayDepositDto payDeposit, HttpServletRequest request) {
        String ip = IpUtil.getIpAddr(request);
        PayOrderBean payOrderBean = saveDepositOrder(user, payParamBean, payDeposit);
        KaliPayReqBody kaliPayReqBody = new KaliPayReqBody();
        kaliPayReqBody.setIp(ip);
        kaliPayReqBody.setAmount(payDeposit.getAmount());
        kaliPayReqBody.setChannel(payDeposit.getPayType());
        kaliPayReqBody.setCurrency("CNY");
        kaliPayReqBody.setCallbackUrl(payParamBean.getCallbackUrl());
        kaliPayReqBody.setKey(payParamBean.getApiKey());
        kaliPayReqBody.setProductId(payParamBean.getApiId());
        kaliPayReqBody.setPaymentReference(payOrderBean.getTenantOrderNo());
        kaliPayReqBody.setPayer(user.getRealName());
        kaliPayReqBody.setLoginName(user.getMobile());
        log.info("deposit: {}", payOrderBean.getTenantOrderNo());
        Map<String, String> data = kaliPayReqBody.getData();
        DataResult dataResult =  OKHttpUtil.httpPostByUrlencoded(payParamBean.getApiUrl() + depositUrl, data);
        return dataResult.getResult();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<String> withdraw(CommonPayParamBean payParamBean, PayWithDrawDto payWithDrawDto) {
        SysParamBean sysParamBean = sysParamBeanMapper.selectByParamName(SysParamConstants.PAY_REVIEW_FLAG);
        UserCardBean userCardBean = userCardBeanMapper.selectById(payWithDrawDto.getCardId());
        UserBean userBean = userMapper.selectById(userCardBean.getUserId());
        UserBalanceBean userBalanceBean = userBalanceBeanMapper.selectUserBalanceByUserId(userBean.getId());

        if (StringUtils.equals(sysParamBean.getParamValue(), "TRUE")) {
            PayOrderBean payOrderBean = saveWithdrawOrder(userBean, payParamBean, payWithDrawDto, userCardBean, false);

            if (userBalanceBean.getAvailableCoin().compareTo(payWithDrawDto.getAmount()) == -1) {
                return ResultUtil.returnError(ResultStatus.NOT_ENOUGH_MONEY);
            }

            int x = changeCoin(userBalanceBean, payOrderBean);
            while (x == 0) {
                userCardBean = userCardBeanMapper.selectById(payWithDrawDto.getCardId());
                userBean = userMapper.selectById(userCardBean.getUserId());
                payOrderBean = saveWithdrawOrder(userBean, payParamBean, payWithDrawDto, userCardBean, false);
                userBalanceBean = userBalanceBeanMapper.selectUserBalanceByUserId(userBean.getId());
                x = changeCoin(userBalanceBean, payOrderBean);
            }
            KaliPayReqBody kaliPayReqBody = new KaliPayReqBody();
            kaliPayReqBody.setAmount(payWithDrawDto.getAmount());
            kaliPayReqBody.setLoginName(userBean.getRealName());
            kaliPayReqBody.setChannel("BANK");
            kaliPayReqBody.setCurrency("CNY");
            kaliPayReqBody.setCallbackUrl(payParamBean.getCallbackUrl());
            kaliPayReqBody.setKey(payParamBean.getApiKey());
            kaliPayReqBody.setProductId(payParamBean.getApiId());
            kaliPayReqBody.setPaymentReference(payOrderBean.getTenantOrderNo());
            kaliPayReqBody.setAccountNumber(userCardBean.getCreditCard());
            kaliPayReqBody.setAccountName(userBean.getRealName());
            kaliPayReqBody.setBranch(userCardBean.getBankProvince() + "|" + userCardBean.getBankCity() + "|" + userCardBean.getBankCounty());
            kaliPayReqBody.setBankName(userCardBean.getBankCode());
            Map<String, String> data = kaliPayReqBody.getWithDrawData();
            DataResult dataResult =  OKHttpUtil.httpPostByUrlencoded(payParamBean.getApiUrl() + withdrawUrl, data);
            return ResultUtil.returnSuccess(dataResult.getResult());
        } else {
            PayOrderBean payOrderBean = saveWithdrawOrder(userBean, payParamBean, payWithDrawDto, userCardBean, true);

            if (userBalanceBean.getAvailableCoin().compareTo(payWithDrawDto.getAmount()) == -1) {
                return ResultUtil.returnError(ResultStatus.NOT_ENOUGH_MONEY);
            }

            int x = changeCoin(userBalanceBean, payOrderBean);
            while (x == 0) {
                userCardBean = userCardBeanMapper.selectById(payWithDrawDto.getCardId());
                userBean = userMapper.selectById(userCardBean.getUserId());
                payOrderBean = saveWithdrawOrder(userBean, payParamBean, payWithDrawDto, userCardBean, true);
                userBalanceBean = userBalanceBeanMapper.selectUserBalanceByUserId(userBean.getId());
                x = changeCoin(userBalanceBean, payOrderBean);
            }
            return ResultUtil.returnSuccess("");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<String> review(CommonPayParamBean payParamBean, Long orderId, OperationResult operate) {
        JSONObject sysUser = (JSONObject) SecurityUtils.getSubject().getSession().getAttribute(Constants.SESSION_USER_INFO);
        PayOrderBean payOrderBean = payOrderBeanMapper.selectById(orderId);
        UserBean user = userMapper.selectById(payOrderBean.getTenantUserNo());
        QueryWrapper<UserCardBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", payOrderBean.getTenantUserNo());
        queryWrapper.eq("credit_card", payOrderBean.getCardNo());
        UserCardBean userCardBean = userCardBeanMapper.selectOne(queryWrapper);
        if (operate.equals(OperationResult.ACCEPT)) {
            KaliPayReqBody kaliPayReqBody = new KaliPayReqBody();
            kaliPayReqBody.setAmount(payOrderBean.getAmount());
            kaliPayReqBody.setLoginName(user.getRealName());
            kaliPayReqBody.setChannel("BANK");
            kaliPayReqBody.setCurrency("CNY");
            kaliPayReqBody.setCallbackUrl(payParamBean.getCallbackUrl());
            kaliPayReqBody.setKey(payParamBean.getApiKey());
            kaliPayReqBody.setProductId(payParamBean.getApiId());
            kaliPayReqBody.setPaymentReference(payOrderBean.getTenantOrderNo());
            kaliPayReqBody.setAccountNumber(userCardBean.getCreditCard());
            kaliPayReqBody.setAccountName(user.getRealName());
            kaliPayReqBody.setBranch(userCardBean.getBankProvince() + "|" + userCardBean.getBankCity() + "|" + userCardBean.getBankCounty());
            kaliPayReqBody.setBankName(userCardBean.getBankCode());
            Map<String, String> data = kaliPayReqBody.getWithDrawData();
            DataResult dataResult =  OKHttpUtil.httpPostByUrlencoded(payParamBean.getApiUrl() + withdrawUrl, data);
        } else {
            payOrderBean.setStatus(OrderStatus.FAIL);
        }

        payOrderBean.setOperationTime(new Date());
        payOrderBean.setOperationResult(operate);
        payOrderBean.setOperatorId(sysUser.getInteger("userId"));
        payOrderService.saveOrUpdate(payOrderBean);

        return ResultUtil.returnSuccess("");
    }

    @Override
    public String getPayType() {
        return "kalipay";
    }

    private PayOrderBean saveDepositOrder(UserBean user, CommonPayParamBean payParamBean, PayDepositDto payDeposit) {
        String orderNo = OrderNoGeneratorUtil.getOrderNoByTime("KALIPAY");
        PayOrderBean payOrderBean = new PayOrderBean();
        payOrderBean.setTenantOrderNo(orderNo);
        payOrderBean.setTenantUserNo(user.getId());
        payOrderBean.setAccessType(AccessType.DEPOSIT);
        payOrderBean.setAmount(payDeposit.getAmount());
        payOrderBean.setChannel(payDeposit.getPayType());
        payOrderBean.setStatus(OrderStatus.UNSETTLED);
        payOrderBean.setOperationResult(OperationResult.NEEDLESS);
        payOrderBeanMapper.insert(payOrderBean);
//        UserBalanceBean userBalanceBean = userBalanceBeanMapper.selectUserBalanceByUserId(user.getId());
//        moneyRecordService.createRecord(payOrderBean.getId(), userBalanceBean.getId(), MoneyAbleType.RECHARGE, userBalanceBean.getCoin(), payDeposit.getAmount(), AlgorithmType.ADD,"xx",userBalanceBean.getUserId());
        return payOrderBean;
    }

    private PayOrderBean saveWithdrawOrder(UserBean user, CommonPayParamBean payParam, PayWithDrawDto payWithDrawDto, UserCardBean userCard, boolean needReview) {
        String orderNo = OrderNoGeneratorUtil.getOrderNoByTime("KALIPAY");
        PayOrderBean payOrderBean = new PayOrderBean();
        payOrderBean.setTenantOrderNo(orderNo);
        payOrderBean.setTenantUserNo(user.getId());
        payOrderBean.setAccessType(AccessType.WITHDRAW);
        payOrderBean.setAmount(payWithDrawDto.getAmount());
        payOrderBean.setChannel("BANK");
        payOrderBean.setStatus(OrderStatus.UNSETTLED);
        payOrderBean.setBankCode(userCard.getBankCode());
        payOrderBean.setBankAddress(userCard.getBankProvince() + "|" + userCard.getBankCity() + "|" + userCard.getBankCounty());
        payOrderBean.setCardNo(userCard.getCreditCard());
        payOrderBean.setCardId(userCard.getId());
        if (needReview) {
            payOrderBean.setOperationResult(OperationResult.WAITING);
        } else {
            payOrderBean.setOperationResult(OperationResult.NEEDLESS);
        }
        payOrderBeanMapper.insert(payOrderBean);
        return payOrderBean;
    }

    private int changeCoin(UserBalanceBean userBalanceBean, PayOrderBean payOrderBean){
        log.info("当前版本号:"+userBalanceBean.getVersion());
        BigDecimal changeCoin = payOrderBean.getAmount();
        userBalanceBean.setAvailableCoin(userBalanceBean.getAvailableCoin().subtract(changeCoin));
        userBalanceBean.setFrozenCoin(userBalanceBean.getFrozenCoin().add(changeCoin));
        return userBalanceBeanMapper.updateById(userBalanceBean);
    }
}
