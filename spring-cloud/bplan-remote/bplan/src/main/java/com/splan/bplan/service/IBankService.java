package com.splan.bplan.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.splan.base.bean.CommonBankBean;
import com.splan.bplan.http.CommonResult;
import com.splan.bplan.result.BankResult;

import java.util.List;

/**
 * Created by lyn on 2019/1/18.
 */
public interface IBankService extends IService<CommonBankBean> {
    CommonResult<List<BankResult>> getBankList();
    CommonResult<IPage<BankResult>> getBankList(IPage<BankResult> page);
}
