package com.splan.bplan.mappers;

import com.splan.base.base.SuperMapper;
import com.splan.base.bean.UserBalanceBean;
import com.splan.base.bean.UserOnlineBean;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserOnlineMapper extends SuperMapper<UserOnlineBean> {
//    @Insert({"insert into user_online (id,online,create_time,update_time) values (#{id,jdbcType.BIGINT},#{online,jdbcType.INTEGER},#{createTime,jdbcType.DATE},#{updateTime,jdbcType.DATE})"})
    @Insert({"insert into user_online (id,online,create_time,update_time) values (#{id},#{online},#{createTime},#{updateTime})"})
    int insert(UserOnlineBean useronlinebean);

    @Select("select * from user_online where  to_days(create_time)=to_days(now())")
    List<UserOnlineBean> selectUserOnlineToday();

//    @Update("update user_online set online =#{update.online} where id=#{update.id}")
//    @UpdateProvider(type = UserOnlineSqlProvider.class,method = "updateUserOnline")
//    int updateUserOnline(@Param("userOnlineBean") UserOnlineBean userOnlineBean);

}
