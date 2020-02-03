package com.splan.bplan.service.impl;

import com.splan.bplan.BplanApplication;
import com.splan.base.bean.UserBalanceBean;
import com.splan.bplan.mappers.UserBalanceBeanMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BplanApplication.class)
public class BetServiceImplTest {

    @Autowired
    private UserBalanceBeanMapper userBalanceBeanMapper;

    @Test
    public void test() {

        BigDecimal totalAmount = new BigDecimal(5);

        UserBalanceBean userBalanceBean = userBalanceBeanMapper.selectUserBalanceByUserId(15l);
        userBalanceBean.setAvailableCoin(userBalanceBean.getAvailableCoin().subtract(totalAmount));//去金额
        userBalanceBean.setFrozenCoin(userBalanceBean.getFrozenCoin().add(totalAmount));//冻结
        userBalanceBeanMapper.updateById(userBalanceBean);
    }
}