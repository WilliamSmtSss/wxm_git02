package com.splan.xbet.frontback.frontback.mappers;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.base.SuperMapper;
import com.splan.base.bean.back.BackProductTrial;
import com.splan.base.enums.CheckStatus;
import com.splan.base.param.backremote.BackProductTrialParam;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BackProductTrialMapper extends SuperMapper<BackProductTrial> {

    @Select("<script>" +
            "select * from back_product_trial t where 1=1 " +
            "<if test='sysId!=null'> and t.sys_id=#{sysId}</if>" +
            "<if test='checkStatus!=null'> and t.status=#{checkStatus}</if>" +
            "</script>")
    BackProductTrial sel1(Integer sysId, CheckStatus checkStatus);

    @Select("<script>select * from back_product_trial t where 1=1 " +
            "<if test='backProductTrialParam.searchText!=null'> and (t.business_name=#{backProductTrialParam.searchText} or t.phone=#{backProductTrialParam.searchText})</if>" +
            "<if test='backProductTrialParam.status!=null'> and t.status=#{backProductTrialParam.status}</if>"+
            "<if test='backProductTrialParam.statuses!=null and backProductTrialParam.statuses.size()!=0'> and t.status in(<foreach collection='backProductTrialParam.statuses' item='status' separator=','>#{status}</foreach>) </if>"+
            "</script>")
    List<BackProductTrial> getList(Page page, BackProductTrialParam backProductTrialParam);

}
