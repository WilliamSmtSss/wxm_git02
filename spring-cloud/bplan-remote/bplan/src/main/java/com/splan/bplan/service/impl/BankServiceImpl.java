package com.splan.bplan.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.splan.base.bean.CommonBankBean;
import com.splan.bplan.http.CommonResult;
import com.splan.bplan.mappers.CommonBankBeanMapper;
import com.splan.bplan.result.BankResult;
import com.splan.bplan.service.IBankService;
import com.splan.bplan.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lyn on 2019/1/18.
 */
@Service
public class BankServiceImpl extends ServiceImpl<CommonBankBeanMapper, CommonBankBean> implements IBankService{

    @Autowired
    private CommonBankBeanMapper commonBankBeanMapper;

    @Override
    public CommonResult<List<BankResult>> getBankList() {
        List<BankResult> bankResultList = commonBankBeanMapper.selectBankList();
        return ResultUtil.returnSuccess(bankResultList);
    }

    @Override
    public CommonResult<IPage<BankResult>> getBankList(IPage<BankResult> page) {
        page.setRecords(commonBankBeanMapper.selectBankPage(page));
        return ResultUtil.returnSuccess(page);
    }
}
