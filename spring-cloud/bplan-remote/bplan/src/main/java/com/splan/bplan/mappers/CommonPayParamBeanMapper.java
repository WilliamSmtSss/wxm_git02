package com.splan.bplan.mappers;

import com.splan.base.bean.CommonPayParamBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CommonPayParamBeanMapper {
    @Insert({
        "insert into common_pay_param (id, type, ",
        "api_id, api_key, api_url, ",
        "callback_url, hrefback_url, ",
        "comment, status)",
        "values (#{id,jdbcType=INTEGER}, #{type,jdbcType=VARCHAR}, ",
        "#{apiId,jdbcType=VARCHAR}, #{apiKey,jdbcType=VARCHAR}, #{apiUrl,jdbcType=VARCHAR}, ",
        "#{callbackUrl,jdbcType=VARCHAR}, #{hrefbackUrl,jdbcType=VARCHAR}, ",
        "#{comment,jdbcType=VARCHAR}, #{status,jdbcType=VARCHAR})"
    })
    int insert(CommonPayParamBean record);

    @InsertProvider(type=CommonPayParamBeanSqlProvider.class, method="insertSelective")
    int insertSelective(CommonPayParamBean record);

    @Select({"select id, type, api_id, api_key, api_url, callback_url, hrefback_url, comment from common_pay_param where status = 'ENABLE'"})
    List<CommonPayParamBean> selectEnableList();
}