package com.splan.xbet.frontback.frontback.mappers;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.base.SuperMapper;
import com.splan.base.bean.back.BackProductInfo;
import com.splan.base.bean.back.BackProductOrder;
import com.splan.base.param.backremote.BackProductOrderParam;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BackProductOrderMapper extends SuperMapper<BackProductOrder> {

    @Select("<script>" +
            "select * from back_product_order t where 1=1 " +
            "<if test='backProductOrderParam.searchText!=null'> and (t.business_name=#{backProductOrderParam.searchText} or t.phone=#{backProductOrderParam.searchText})</if>"+
            "<if test='backProductOrderParam.serviceId!=null'> and t.service_id=#{backProductOrderParam.serviceId} </if>"+
            "<if test='backProductOrderParam.statuses!=null and backProductOrderParam.statuses.size()!=0'> and t.status in(<foreach collection='backProductOrderParam.statuses' item='status' separator=','>#{status}</foreach>) </if>"+
            "</script>")
    List<BackProductOrder> getPageList(Page page, BackProductOrderParam backProductOrderParam);

    @Select("select count(1) from back_product_order t where t.status='PASS' and t.business_name=#{apiId}")
    Integer getPassOrder(String apiId);
}
