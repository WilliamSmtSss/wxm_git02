package com.splan.bplan.mappers;

import com.splan.base.base.SuperMapper;
import com.splan.base.bean.BusinessCurrencyConfigBean;
import org.apache.ibatis.annotations.Select;

public interface BusinessCurrencyConfigBeanMapper extends SuperMapper<BusinessCurrencyConfigBean> {


    @Select("select * from business_currency_config where currency=#{currency}")
    BusinessCurrencyConfigBean selectByCurrency(Integer currency);
}
