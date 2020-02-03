package com.splan.xbet.admin.back.mappers;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.BusinessConfigBean;
import com.splan.xbet.admin.back.base.SuperMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import springfox.documentation.service.ApiListing;

import java.util.List;

public interface BusinessConfigBeanMapper extends SuperMapper<BusinessConfigBean> {
    //    @Select("<script>" +
//            "select * from x_order_data_view t <where> 1=1 " +
//            "<if></if>" +
//            "</where>" +
//            "</script>")

    @Select("<script>" +
            "select t.*,ifnull(t2.username,'') as sys_user_name,ifnull(t3.code,'') as currency_name from business_config t left join (select * from sys_user temp where temp.role_id!=0 ) t2 on t.sys_user_id=t2.id left join business_currency_config t3 on t.currency=t3.id<where> 1=1 " +
            "<if test='status!=null'> and t.status=#{status}</if>"+
            "<if test='businessName!=null'> and t.api_id=#{businessName}</if>"+
            "<if test='businessType!=null and businessType==\"0\"'> and t.pid is null</if>"+
            "<if test='businessType!=null and businessType==\"1\"'> and t.pid is not null</if>"+
            "<if test='pid!=null'> and t.pid =#{pid}</if>"+
            "</where>"+
            " order by t.create_time desc"+
            "</script>")
    List<BusinessConfigBean> getList(Page page,String status, String businessName,String businessType,String pid);



    @Select("select count(1) from business_config t where t.status='1' and t.pid=#{id}")
    Integer getDownBusCount(@Param("id") String id);


    @Select("select * from business_config t where  t.api_id=#{apiId}")
    BusinessConfigBean getByApiId(@Param("apiId") String apiId);

    @Select("select t1.* from business_config t1 left join business_config t2 on t1.pid=t2.id where (t2.api_id=#{apiId} or t1.api_id=#{apiId})")
    List<BusinessConfigBean> getListByapiId(String apiId);

    //v2
    @Select("<script>" +
            "select t.*,t2.code as currency_name from business_config t left join business_currency_config t2 on t.currency=t2.id<where> 1=1 " +
            "<if test='apiId!=null'> and t.api_id=#{apiId}</if>"+
            "</where>"+
            " order by t.create_time desc"+
            "</script>")
    List<BusinessConfigBean> getListv2(Page page, String apiId);

}
