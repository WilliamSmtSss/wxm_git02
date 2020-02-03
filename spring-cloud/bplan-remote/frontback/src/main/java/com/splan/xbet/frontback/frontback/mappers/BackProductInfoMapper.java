package com.splan.xbet.frontback.frontback.mappers;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.base.SuperMapper;
import com.splan.base.bean.back.BackProductInfo;
import com.splan.base.enums.CheckStatus;
import org.apache.ibatis.annotations.Select;
import org.omg.CORBA.INTERNAL;

import java.util.List;

public interface BackProductInfoMapper extends SuperMapper<BackProductInfo> {

    @Select("select t1.*,ifnull(t2.`status`,'UNABLE') as check_status,t2.service_starttime as service_start,t2.service_endtime as service_end from back_product_info t1 LEFT JOIN (select * from back_product_order t where t.business_name=#{businessName}) t2 on t1.id=t2.service_id")
    List<BackProductInfo> getList(Page page,String businessName);

    @Select("<script>" +
            "select * from back_product_info t where 1=1 " +
            "<if test='checkStatus!=null'> and t.status=#{checkStatus}</if>"+
            "</script>")
    List<BackProductInfo> getPageList(Page page, CheckStatus checkStatus);

}
