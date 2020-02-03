package com.splan.bplan.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.splan.base.bean.PayOrderBean;
import com.splan.base.enums.*;
import com.splan.bplan.http.CommonResult;
import com.splan.bplan.mappers.PayOrderBeanMapper;
import com.splan.bplan.mappers.UserBalanceBeanMapper;
import com.splan.bplan.result.OperateOrderResult;
import com.splan.bplan.result.OperateResult;
import com.splan.bplan.result.PayOrderResult;
import com.splan.bplan.service.IMoneyRecordService;
import com.splan.bplan.service.IPayOrderService;
import com.splan.bplan.service.IUserBalanceService;
import com.splan.bplan.utils.DateUtil;
import com.splan.bplan.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@Slf4j
public class PayOrderServiceImpl extends ServiceImpl<PayOrderBeanMapper, PayOrderBean> implements IPayOrderService {

    @Autowired
    private PayOrderBeanMapper payOrderBeanMapper;

    @Autowired
    private UserBalanceBeanMapper userBalanceBeanMapper;

    @Autowired
    private IUserBalanceService userBalanceService;

    @Autowired
    private IMoneyRecordService moneyRecordService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<String> updateOrder(String orderNo, String status, BigDecimal amount) {
        PayOrderBean payOrderBean = payOrderBeanMapper.selectByTenantOrderNo(orderNo);
        if (null == payOrderBean) {
            return ResultUtil.returnError(ResultStatus.PAY_ORDER_NOT_FOUND);
        }
        if (payOrderBean.getStatus().equals(OrderStatus.SETTLED)) {
            return ResultUtil.returnError(ResultStatus.PAY_ORDER_IS_SETTLED);
        }
        if (!payOrderBean.getAmount().setScale(0, RoundingMode.HALF_UP).equals(amount)) {
            return ResultUtil.returnError(ResultStatus.PAY_ORDER_NOT_EQUAL);
        }
        payOrderBean.setStatus(status.equals("ok") ? OrderStatus.SETTLED : OrderStatus.FAIL);
        if (payOrderBean.getAccessType().equals(AccessType.DEPOSIT)) {
            userBalanceService.changeCoin(payOrderBean.getTenantUserNo(), payOrderBean.getId(), payOrderBean.getAmount(), MoneyAbleType.RECHARGE);
        } else {
            userBalanceService.changeCoin(payOrderBean.getTenantUserNo(), payOrderBean.getId(), payOrderBean.getAmount(), amount, MoneyAbleType.WITHDRAW);
        }
        updateById(payOrderBean);
        return ResultUtil.returnSuccess("");
    }

    @Override
    public JSONObject statisticsDeposit() {
        JSONObject jsonObject = new JSONObject();
        QueryWrapper<PayOrderBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("access_type", AccessType.DEPOSIT);
        jsonObject.put("totalCount", count(queryWrapper));
        queryWrapper.eq("date_format(create_time, '%Y-%m-%d')", DateUtil.dateToString(new Date()));
        jsonObject.put("dayCount", count(queryWrapper));
        return jsonObject;
    }

    @Override
    public IPage<PayOrderResult> selectPage(IPage<PayOrderResult> page, Date startDate, Date endDate, AccessType accessType, OrderStatus orderStatus) {
        List<PayOrderResult> list = payOrderBeanMapper.selectOrderPage(page, accessType, startDate, endDate, null, orderStatus);
        page.setRecords(list);
        return page;
    }

    @Override
    public JSONObject statisticsWithdraw() {
        JSONObject jsonObject = new JSONObject();
        QueryWrapper<PayOrderBean> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("access_type", AccessType.WITHDRAW);
        jsonObject.put("totalCount", count(queryWrapper));
        queryWrapper.eq("11date_format(create_time, '%Y-%m-%d')", DateUtil.dateToString(new Date()));
        jsonObject.put("dayCount", count(queryWrapper));
        return jsonObject;
    }

    @Override
    public JSONObject statisticsWithdraw(OperationResult... operationResults) {
        JSONObject jsonObject = new JSONObject();
        int totalCount = 0;
        int dayCount = 0;
        for (OperationResult operationResult : operationResults) {
            QueryWrapper<PayOrderBean> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("access_type", AccessType.WITHDRAW);
            queryWrapper.eq("operation_result", operationResult);
            totalCount += count(queryWrapper);
            queryWrapper.eq("date_format(create_time, '%Y-%m-%d')", DateUtil.dateToString(new Date()));
            dayCount += count(queryWrapper);
        }
        jsonObject.put("totalCount", totalCount);
        jsonObject.put("dayCount", dayCount);
        return jsonObject;
    }

    @Override
    public IPage<PayOrderResult> selectPage(IPage<PayOrderResult> page, Date startDate, Date endDate, AccessType accessType,
                                            OperationResult... operationResults) {
        StringBuffer sb = new StringBuffer();
        List<OperationResult> operationResult = Arrays.asList(operationResults);
        if (operationResult.size() == 0) {
            operationResult = null;
        }
        List<PayOrderResult> list = payOrderBeanMapper.selectOrderPage(page, accessType, startDate, endDate, operationResult, null);
        page.setRecords(list);
        return page;
    }

    @Override
    public IPage<PayOrderResult> selectAgentPage(IPage<PayOrderResult> page, Date startDate, Date endDate, AccessType accessType, OperationResult... operationResults) {
        StringBuffer sb = new StringBuffer();
        List<OperationResult> operationResult = Arrays.asList(operationResults);
        if (operationResult.size() == 0) {
            operationResult = null;
        }
        List<PayOrderResult> list = payOrderBeanMapper.selectOrderPage(page, accessType, startDate, endDate, operationResult, null);
        page.setRecords(list);
        return page;
    }

    //    private int changeCoin(UserBalanceBean userBalanceBean, PayOrderBean payOrderBean, String status){
//        log.info("当前版本号:"+userBalanceBean.getVersion());
//        BigDecimal changeCoin = payOrderBean.getAmount();
//        if (payOrderBean.getAccessType().equals(AccessType.DEPOSIT)) {
//            if (StringUtils.equals(status, "ok")) {
//                userBalanceBean.setAvailableCoin(userBalanceBean.getAvailableCoin().add(changeCoin));
//                userBalanceBean.setCoin(userBalanceBean.getCoin().add(changeCoin));
//                userBalanceBean.setDepositCoin(userBalanceBean.getDepositCoin().add(changeCoin));
//            }
//        } else {
//            if (StringUtils.equals(status, "ok")) {
//                userBalanceBean.setFrozenCoin(userBalanceBean.getFrozenCoin().subtract(changeCoin));
//                userBalanceBean.setWithdrawCoin(userBalanceBean.getWithdrawCoin().add(changeCoin));
//                userBalanceBean.setCoin(userBalanceBean.getCoin().subtract(changeCoin));
//            } else {
//                userBalanceBean.setFrozenCoin(userBalanceBean.getFrozenCoin().subtract(changeCoin));
//                userBalanceBean.setAvailableCoin(userBalanceBean.getAvailableCoin().add(changeCoin));
//            }
//        }
//        return userBalanceBeanMapper.updateById(userBalanceBean);
//    }
//
//    private void process(PayOrderBean payOrderBean, String status){
//        synchronized (payOrderBean.getTenantUserNo().toString().intern()){
//            UserBalanceBean userBalanceBean = userBalanceBeanMapper.selectUserBalanceByUserId(payOrderBean.getTenantUserNo());
//            BigDecimal from = userBalanceBean.getCoin();
//            int x = changeCoin(userBalanceBean, payOrderBean, status);
//            while (x==0){
//                userBalanceBean = userBalanceBeanMapper.selectUserBalanceByUserId(payOrderBean.getTenantUserNo());
//                from = userBalanceBean.getCoin();
//                x = changeCoin(userBalanceBean, payOrderBean, status);
//            }
//            AlgorithmType algorithmType = payOrderBean.getAccessType().equals(AccessType.DEPOSIT) ? AlgorithmType.ADD : AlgorithmType.SUB;
//            moneyRecordService.createRecord(payOrderBean.getId(), userBalanceBean.getId(), MoneyAbleType.BET,from,payOrderBean.getAmount(), algorithmType,"xx",userBalanceBean.getUserId());
//        }
//
//    }

    @Override
    public IPage<OperateResult> operateList(IPage<OperateResult> page) {
        List<OperateResult> list=payOrderBeanMapper.operateList(page);
        page.setRecords(list);
        return page;
    }

    @Override
    public BigDecimal getTodayamountByUserType(List<String> types) {
        return payOrderBeanMapper.getTodayamountByUserType(types);
    }

    @Override
    public JSONObject queryWitdraw(String userType,String orderType) {
        JSONObject result=new JSONObject();
        List<Level> levels=new ArrayList<>();
        List<OperationResult> operationResults=new ArrayList<>();
        //用户提款总额，今日，昨日
        if("user".equals(userType)&&"".equals(orderType)){
            result.put("totleAmount",payOrderBeanMapper.queryWitdraw(null,null,-1));
            result.put("todayAmount",payOrderBeanMapper.queryWitdraw(null,null,0));
            result.put("yestTodayAmount",payOrderBeanMapper.queryWitdraw(null,null,1));
        }
        //用户待处理
        else if("user".equals(userType)&&"waiting".equals(orderType)){
            operationResults.add(OperationResult.WAITING);
            result.put("totleAmount",payOrderBeanMapper.queryWitdraw(null,operationResults,-1));
            result.put("todayAmount",payOrderBeanMapper.queryWitdraw(null,operationResults,0));
            result.put("yestTodayAmount",payOrderBeanMapper.queryWitdraw(null,operationResults,1));
        }
        //用户已处理
        else if("user".equals(userType)&&"already".equals(orderType)){
            operationResults.add(OperationResult.ACCEPT);
            operationResults.add(OperationResult.NEEDLESS);
            operationResults.add(OperationResult.REJECT);
            result.put("totleAmount",payOrderBeanMapper.queryWitdraw(null,operationResults,-1));
            result.put("todayAmount",payOrderBeanMapper.queryWitdraw(null,operationResults,0));
            result.put("yestTodayAmount",payOrderBeanMapper.queryWitdraw(null,operationResults,1));
        }
        //代理总提款
        else if("agent".equals(userType)&&"".equals(orderType)){
            levels.add(Level.Agent);
            levels.add(Level.SuperAgent);
            result.put("totleAmount",payOrderBeanMapper.queryWitdraw(levels,null,-1));
            result.put("todayAmount",payOrderBeanMapper.queryWitdraw(levels,null,0));
            result.put("yestTodayAmount",payOrderBeanMapper.queryWitdraw(levels,null,1));
        }
        //代理待处理
        else if("agent".equals(userType)&&"waiting".equals(orderType)){
            levels.add(Level.Agent);
            levels.add(Level.SuperAgent);
            operationResults.add(OperationResult.WAITING);
            result.put("totleAmount",payOrderBeanMapper.queryWitdraw(levels,operationResults,-1));
            result.put("todayAmount",payOrderBeanMapper.queryWitdraw(levels,operationResults,0));
            result.put("yestTodayAmount",payOrderBeanMapper.queryWitdraw(levels,operationResults,1));
        }
        //代理已处理
        else if("agent".equals(userType)&&"already".equals(orderType)){
            levels.add(Level.Agent);
            levels.add(Level.SuperAgent);
            operationResults.add(OperationResult.ACCEPT);
            operationResults.add(OperationResult.NEEDLESS);
            operationResults.add(OperationResult.REJECT);
            result.put("totleAmount",payOrderBeanMapper.queryWitdraw(levels,operationResults,-1));
            result.put("todayAmount",payOrderBeanMapper.queryWitdraw(levels,operationResults,0));
            result.put("yestTodayAmount",payOrderBeanMapper.queryWitdraw(levels,operationResults,1));
        }
         return result;
    }
}
