package com.splan.bplan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.splan.base.bean.UserBalanceBean;
import com.splan.base.enums.MoneyAbleType;

import java.math.BigDecimal;

public interface IUserBalanceService extends IService<UserBalanceBean> {

    UserBalanceBean changeCoin(Long userId,Long dataId, BigDecimal amount, MoneyAbleType moneyAbleType);

    UserBalanceBean changeCoin(Long userId, Long dataId, BigDecimal orderAmount, BigDecimal realAmount, MoneyAbleType moneyAbleType);
}
