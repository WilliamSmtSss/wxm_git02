package com.splan.bplan.service;

import com.splan.base.bean.MoneyRecordBean;
import com.splan.base.enums.AlgorithmType;
import com.splan.base.enums.MoneyAbleType;

import java.math.BigDecimal;

public interface IMoneyRecordService {

    /**
     * 增加一条流水
     * @return
     */
    MoneyRecordBean createRecord(Long dataId, Long balanceId, MoneyAbleType moneyAbleType,BigDecimal from, BigDecimal amount, AlgorithmType algorithmType,String detail,Long modifyId);

    /**
     * 增加一条流水
     * @return
     */
    MoneyRecordBean createRecord(Long dataId, Long balanceId, MoneyAbleType moneyAbleType,BigDecimal from, BigDecimal amount, AlgorithmType algorithmType,Long modifyId);
}
