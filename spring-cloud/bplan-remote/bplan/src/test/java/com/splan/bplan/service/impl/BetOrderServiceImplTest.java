package com.splan.bplan.service.impl;

import com.splan.bplan.BplanApplication;
import com.splan.base.bean.BetOptionBean;
import com.splan.base.bean.BetOrderBean;
import com.splan.base.bean.BetOrderDetailBean;
import com.splan.base.enums.OrderStatus;
import com.splan.bplan.mappers.BetOptionBeanMapper;
import com.splan.bplan.mappers.BetOrderDetailBeanMapper;
import com.splan.bplan.service.IBetOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BplanApplication.class)
public class BetOrderServiceImplTest {

    @Autowired
    private IBetOrderService betOrderService;

    @Autowired
    private BetOptionBeanMapper betOptionBeanMapper;

    @Autowired
    private BetOrderDetailBeanMapper betOrderDetailBeanMapper;

    @Test
    public void checkResult() {

        /*BetOptionBean betOptionBean = betOptionBeanMapper.selectById(3993);
        betOrderService.checkResult(betOptionBean);*/
    }

    @Test
    public  void sss(){
        //List<BetOrderDetailBean> betOrderDetailBeans = betOrderDetailBeanMapper.selectByBetOptionId(5068, OrderStatus.UNSETTLED);
        BetOptionBean betOptionBean = betOptionBeanMapper.selectById(5068);
        List<BetOptionBean> betOrderDetailBeans = new ArrayList<>();
        betOrderDetailBeans.add(betOptionBean);
        betOrderService.checkResult(betOrderDetailBeans);
        //System.out.println(betOrderDetailBeans);
    }
}