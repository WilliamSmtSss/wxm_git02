package com.splan.xbet.frontback.frontback.mappers;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.base.SuperMapper;
import com.splan.base.bean.back.BackNotice;
import com.splan.base.bean.back.BackProductInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BackNoticeMapper extends SuperMapper<BackNotice> {

    @Select("<script>" +
            "select * from back_notice t <where> 1=1 " +
            "<if test='query.type != null'> and t.type = #{query.type} </if>"+
            "<if test='query.product != null'> and t.product = #{query.product} </if>"+
            "<if test='query.status != null'> and t.status = #{query.status} </if>"+
            "<if test='query.statuss != null and query.size()!=0 '> and t.status in (<foreach collection='query.statuss' item='status' separator=','>#{status}</foreach>) </if>"+
            "</where>"+
            " order by (case when t.zd_id=1 then 1 else 2 end),t.create_time desc"+
            "</script>")
    List<BackNotice> pageList(Page page, @Param("query") BackNotice backNotice);


}
