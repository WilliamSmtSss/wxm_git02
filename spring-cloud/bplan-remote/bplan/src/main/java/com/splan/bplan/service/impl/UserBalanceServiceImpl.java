package com.splan.bplan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.splan.base.bean.UserBalanceBean;
import com.splan.bplan.constants.SysParamConstants;
import com.splan.base.enums.AlgorithmType;
import com.splan.base.enums.MoneyAbleType;
import com.splan.bplan.mappers.UserBalanceBeanMapper;
import com.splan.bplan.service.IMoneyRecordService;
import com.splan.bplan.service.IUserBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class UserBalanceServiceImpl extends ServiceImpl<UserBalanceBeanMapper, UserBalanceBean> implements IUserBalanceService {

    @Autowired
    private UserBalanceBeanMapper userBalanceBeanMapper;

    @Autowired
    private IMoneyRecordService moneyRecordService;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE,rollbackFor = Exception.class)
    public UserBalanceBean changeCoin(Long userId, Long dataId,BigDecimal amount, MoneyAbleType moneyAbleType) {
        UserBalanceBean userBalanceBean = null;
        BigDecimal limit = null;
        BigDecimal from = null;
        int x = 0;
        while (x == 0) {
            userBalanceBean = userBalanceBeanMapper.selectUserBalanceByUserId(userId);
            from = userBalanceBean.getCoin();
            if (moneyAbleType.equals(MoneyAbleType.RECHARGE)) {//充值
                limit = amount.multiply(new BigDecimal(SysParamConstants.getMap().get(SysParamConstants.LIMIT_CHARGE).toString()));
                limit.setScale(0, BigDecimal.ROUND_UP);
                userBalanceBean.setDepositCoin(userBalanceBean.getDepositCoin().add(amount));
            } else if (moneyAbleType.equals(MoneyAbleType.ACTIVITY)) {//活动
                limit = amount.multiply(new BigDecimal(SysParamConstants.getMap().get(SysParamConstants.LIMIT_GIFT).toString()));
                limit.setScale(0, BigDecimal.ROUND_UP);
                userBalanceBean.setGiftCoin(userBalanceBean.getGiftCoin().add(amount));
            } else if (moneyAbleType.equals(MoneyAbleType.WATER)) {//返水
                limit = amount.multiply(new BigDecimal(SysParamConstants.getMap().get(SysParamConstants.LIMIT_WATER).toString()));
                limit.setScale(0, BigDecimal.ROUND_UP);
                userBalanceBean.setBrokerageCoin(userBalanceBean.getBrokerageCoin().add(amount));
            } else if (moneyAbleType.equals(MoneyAbleType.EVERYDAY_ACTIVITY)){
                limit = amount.multiply(new BigDecimal(SysParamConstants.getMap().get(SysParamConstants.LIMIT_EVERYDAY_ACTIVITY).toString()));
                limit.setScale(0, BigDecimal.ROUND_UP);
                userBalanceBean.setBrokerageCoin(userBalanceBean.getGiftCoin().add(amount));
            }
            userBalanceBean.setLimitCoin(userBalanceBean.getLimitCoin().add(limit));//增加限额
            userBalanceBean.setCoin(userBalanceBean.getCoin().add(amount));
            userBalanceBean.setAvailableCoin(userBalanceBean.getAvailableCoin().add(amount));
            x = userBalanceBeanMapper.updateById(userBalanceBean);
        }
        moneyRecordService.createRecord(dataId,userBalanceBean.getId(),moneyAbleType,from,amount, AlgorithmType.ADD,userId);
        return userBalanceBean;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE,rollbackFor = Exception.class)
    public UserBalanceBean changeCoin(Long userId, Long dataId, BigDecimal orderAmount, BigDecimal realAmount, MoneyAbleType moneyAbleType) {
        UserBalanceBean userBalanceBean = null;
        BigDecimal from = null;
        int x = 0;

        switch (moneyAbleType) {
            case WITHDRAW:
                while (x == 0) {
                    userBalanceBean = userBalanceBeanMapper.selectUserBalanceByUserId(userId);
                    from = userBalanceBean.getCoin();
                    if (moneyAbleType.equals(MoneyAbleType.WITHDRAW)) {//充值
                        userBalanceBean.setWithdrawCoin(userBalanceBean.getWithdrawCoin().add(realAmount));
                    }
                    userBalanceBean.setCoin(userBalanceBean.getCoin().subtract(realAmount));
                    userBalanceBean.setAvailableCoin(userBalanceBean.getAvailableCoin().add(orderAmount).subtract(realAmount));
                    userBalanceBean.setFrozenCoin(userBalanceBean.getFrozenCoin().subtract(orderAmount).add(realAmount));
                    x = userBalanceBeanMapper.updateById(userBalanceBean);
                }
                moneyRecordService.createRecord(dataId,userBalanceBean.getId(),moneyAbleType,from, realAmount, AlgorithmType.SUB,userId);
                break;
            default: break;
        }

        return userBalanceBean;
    }
}
