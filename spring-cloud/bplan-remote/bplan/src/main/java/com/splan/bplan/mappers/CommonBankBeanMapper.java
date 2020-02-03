package com.splan.bplan.mappers;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.base.SuperMapper;
import com.splan.base.bean.CommonBankBean;
import com.splan.bplan.result.BankResult;
import org.apache.ibatis.annotations.*;

import java.sql.Wrapper;
import java.util.List;

public interface CommonBankBeanMapper extends SuperMapper<CommonBankBean> {


    @InsertProvider(type=CommonBankBeanSqlProvider.class, method="insertSelective")
    int insertSelective(CommonBankBean record);

    @Select({"select id, bank_name, status, bank_code from common_bank where status = 'ENABLE'"})
    List<BankResult> selectBankList();

    @Select({"select id, bank_name, status, bank_code, bank_icon from common_bank where status = 'ENABLE'"})
    List<BankResult> selectBankPage(IPage page);
}