package com.splan.xbet.admin.back.mappers;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.BusinessCurrencyRecordBean;
import com.splan.xbet.admin.back.base.SuperMapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

public interface BusinessCurrencyRecordBeanMapper extends SuperMapper<BusinessCurrencyRecordBean> {
    @Select("<script>" +
            "select * from business_currency_record t <where> 1=1 " +
            " <if test='startDate != null '> AND to_days(t.change_time) <![CDATA[>=]]> to_days(#{startDate}) </if> "+
            " <if test='endDate != null '> AND to_days(t.change_time) <![CDATA[<=]]> to_days(#{endDate}) </if> "+
            " <if test='currencyId != null '> AND t.currency_id=#{currencyId} </if> "+
            " order by t.create_time desc"+
            "</where>"+
            "</script>")
    List<BusinessCurrencyRecordBean> getPageList(Page page, Date startDate, Date endDate,String currencyId);
}
