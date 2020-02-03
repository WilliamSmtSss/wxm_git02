package com.splan.bplan.service.impl;

import com.splan.base.bean.MoneyRecordBean;
import com.splan.base.bean.UserBalanceBean;
import com.splan.base.enums.AlgorithmType;
import com.splan.base.enums.MoneyAbleType;
import com.splan.bplan.mappers.MoneyRecordBeanMapper;
import com.splan.bplan.mappers.UserBalanceBeanMapper;
import com.splan.bplan.service.IMoneyRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class MoneyRecordServiceImpl implements IMoneyRecordService {

    @Autowired
    private MoneyRecordBeanMapper moneyRecordBeanMapper;

    @Autowired
    private UserBalanceBeanMapper userBalanceBeanMapper;


    @Override
    public MoneyRecordBean createRecord(Long dataId, Long balanceId, MoneyAbleType moneyAbleType,BigDecimal from, BigDecimal amount, AlgorithmType algorithmType, String detail,Long modifyId) {
        //UserBalanceBean userBalanceBean = userBalanceBeanMapper.selectById(balanceId);
        MoneyRecordBean moneyRecordBean = new MoneyRecordBean();
        moneyRecordBean.setBalanceId(balanceId);
        moneyRecordBean.setMoneyableId(dataId);
        moneyRecordBean.setMoneyableType(moneyAbleType);
        moneyRecordBean.setAlgorithm(algorithmType);
        moneyRecordBean.setAmount(amount);

        moneyRecordBean.setMoneyFrom(from);
        if (algorithmType.equals(AlgorithmType.SUB)){
            moneyRecordBean.setMoneyTo(from.subtract(amount));
        }else {
            moneyRecordBean.setMoneyTo(from.add(amount));
        }
        //moneyRecordBean.setMoneyTo(from.subtract(amount));
        moneyRecordBean.setModifyId(modifyId);
        if (detail.length()>200){
            detail = detail.substring(0,200);
        }
        moneyRecordBean.setDetail(detail);
        moneyRecordBean.setCreateTime(new Date());
        moneyRecordBean.setUpdateTime(new Date());
        moneyRecordBeanMapper.insert(moneyRecordBean);
        return moneyRecordBean;
    }

    @Override
    public MoneyRecordBean createRecord(Long dataId, Long balanceId, MoneyAbleType moneyAbleType,BigDecimal from, BigDecimal amount, AlgorithmType algorithmType,Long modifyId) {
        return createRecord(dataId,balanceId,moneyAbleType,from,amount,algorithmType,"",modifyId);
    }
}
