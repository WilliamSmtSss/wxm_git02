package com.splan.xbet.admin.back.mappers;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.BusinessCurrencyConfigBean;
import com.splan.xbet.admin.back.base.SuperMapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

public interface BusinessCurrencyConfigBeanMapper extends SuperMapper<BusinessCurrencyConfigBean> {
    @Select("<script>" +
            "select * from business_currency_config t <where> 1=1 " +
            " <if test='startDate != null '> AND to_days(t.update_time) <![CDATA[>=]]> to_days(#{startDate}) </if> "+
            " <if test='endDate != null '> AND to_days(t.update_time) <![CDATA[<=]]> to_days(#{endDate}) </if> "+
            "</where>"+
            "</script>")
    List<BusinessCurrencyConfigBean> getPageList(Page page, Date startDate, Date endDate);
}
