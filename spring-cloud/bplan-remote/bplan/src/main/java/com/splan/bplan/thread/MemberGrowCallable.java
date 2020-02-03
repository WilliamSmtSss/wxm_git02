package com.splan.bplan.thread;

import java.math.BigDecimal;
import java.util.concurrent.Callable;

/**
 * 会员结算
 */
public class MemberGrowCallable implements Callable<String> {

    private Long userId;

    private BigDecimal orderAmount;

    public MemberGrowCallable(Long userId,BigDecimal orderAmount){
        this.userId = userId;
        this.orderAmount = orderAmount;
    }

    @Override
    public String call() throws Exception {


        return null;
    }
}
