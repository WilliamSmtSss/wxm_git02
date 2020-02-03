package com.splan.xbet.frontback.frontback.mappers;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.base.SuperMapper;
import com.splan.base.bean.back.BackMessage;
import com.splan.base.bean.back.BackNotice;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface BackMessageMapper extends SuperMapper<BackMessage> {

    @Update("update back_message t set t.status=1 where t.msg_id=#{msgId} and t.msg_type=#{msgType}")
    int updateByMsgId(String msgId,String msgType);

    @Select("<script> select * from back_message t <where> 1=1 " +
            "<if test='query.msgType!=null'> and t.msg_type=#{query.msgType}</if>" +
            "<if test='query.status!=null'> and t.status=#{query.status}</if>" +
            "</where>"+
            "</script>")
    List<BackMessage> pageList(Page page,@Param("query") BackMessage query);

}
