package com.splan.bplan.mappers;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.base.SuperMapper;
import com.splan.base.bean.UserBean;
import com.splan.base.enums.Level;
import com.splan.bplan.result.GameResult;
import com.splan.bplan.result.MemberResult;
import com.splan.bplan.result.UserAccountResult;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper extends SuperMapper<UserBean> {

    @Select("select * from user_account where username=#{username}")
    UserBean findByUsername(String username);

    @Select("select * from user_account where extra_id=#{username}")
    UserBean findByUsername2(String username);
    @Select("select * from user_account where mobile_area=#{mobileArea} and mobile=#{mobile}")
    UserBean findByMobile(String mobileArea,String mobile);

    @Select("select * from user_account where extra_id=#{extraId} and api_id=#{apiId}")
    UserBean findByApiIdAndExtraId(String apiId,String extraId);

    @Select("<script> select a.*,b.* from user_account as a left join user_balance as b on a.id=b.user_id <where> 1=1  " +
            " <if test=\"userId != null \"> AND a.id =#{userId} </if> "+
            " <if test=\"mobile != null and mobile !='' \"> AND a.mobile=#{mobile} </if> "+
            " <if test='levels != null '> AND level in (" +
            " <foreach collection='levels' item='level' separator=','> #{level}" +
            " </foreach>) </if> "+
            "</where> </script>")
    @Results({ @Result(column = "invite_code",property = "agentids",many = @Many(select = "com.splan.bplan.mappers.UserMapper.getAllUsersByCode")),
//            @Result(column = "invite_code",property = "normals",many = @Many(select = "com.splan.bplan.mappers.UserMapper.getNormalUsersByCode")),
    })
    List<UserAccountResult> selectAll(IPage<UserAccountResult> UserBean, Long userId, String mobile,List<Level> levels);

    @Select("<script> SELECT a.id,a.username,a.mobile,a.real_name,a.`status`,a.create_time,a.last_login_time,a.register_channel,a.invite_code,b.brokerage_coin,ifnull(c.total_invite,0) as totalInvite,ifnull(c.total_deposit_coin,0) as totalDepositCoin FROM user_account AS a LEFT JOIN user_balance AS b ON a.id=b.user_id LEFT JOIN (" +
            "SELECT count(*) AS total_invite,be_invite_code,SUM(d.deposit_coin) AS total_deposit_coin FROM user_account AS c LEFT JOIN user_balance AS d ON c.id=d.user_id and c.be_invite_code is not null GROUP BY be_invite_code) AS c ON c.be_invite_code=a.invite_code <where> 1=1  " +
            " <if test=\"userId != null \"> AND a.id =#{userId} </if> "+
            " <if test=\"mobile != null and mobile !='' \"> AND a.mobile=#{mobile} </if> "+
            "</where> </script>")
    List<UserAccountResult> selectMoneyAll(IPage<UserAccountResult> UserBean, Long userId, String mobile);

    @Select("SELECT a.real_name,a.member_level,a.member_status,c.level_name,b.order_coin,c.stay_order_coin,c.up_order_coin,c.level_start_coin FROM user_account AS a LEFT JOIN user_balance AS b ON a.id=b.user_id LEFT JOIN member_interests AS c ON a.member_level=c.id WHERE a.id=#{userId}")
    MemberResult findMemberByUserId(Long userId);

    @Select("<script>select * from user_account " +
            "<where> id in (" +
            " <foreach collection='userId' item='id' separator=','> #{id}" +
            " </foreach>)" +
            "</where>" +
            "</script>")
    @Results({
            @Result(id=true,property="id",column="id",javaType=Long.class),
            @Result(column = "id",property = "userBalanceBean",many = @Many(select = "com.splan.bplan.mappers.UserBalanceBeanMapper.selectUserBalanceByUserId")),
            @Result(column = "id",property = "ordertotle",many = @Many(select = "com.splan.bplan.mappers.BetOrderBeanMapper.getTotleOrderByuserId")),
            @Result(column = "id",property = "rewardtotle",many = @Many(select = "com.splan.bplan.mappers.BetOrderBeanMapper.getTotleRewardByuserId")),
    })
    List<UserBean> selectUserPage(IPage page, List<Long> userId);

    @Select("<script>select * from user_account " +
            "<where> id in (" +
            " <foreach collection='userId' item='id' separator=','> #{id}" +
            " </foreach>)" +
            "</where>" +
            "</script>")
    @Results({
            @Result(id=true,property="id",column="id",javaType=Long.class),
            @Result(column = "id",property = "userBalanceBean",many = @Many(select = "com.splan.bplan.mappers.UserBalanceBeanMapper.selectUserBalanceByUserId")),
            @Result(column = "id",property = "ordertotle",many = @Many(select = "com.splan.bplan.mappers.BetOrderBeanMapper.getTotleOrderByuserId")),
            @Result(column = "id",property = "rewardtotle",many = @Many(select = "com.splan.bplan.mappers.BetOrderBeanMapper.getTotleRewardByuserId")),
    })
    List<UserBean> selectUserNoPage(@Param("userId") List<Long> userId);

    @Select("select t.id from  user_account t where t.level='Agent' and t.be_invite_code=#{code}")
    List<Integer> getAgentUsersByCode(String code);

    @Select("select t.id from  user_account t where t.level='Normal' and t.be_invite_code=#{code}")
    List<Integer> getNormalUsersByCode(String code);

    @Select("select t.id from  user_account t where t.be_invite_code=#{code}")
    List<Integer> getAllUsersByCode(String code);

    @Select("<script>select t.id from user_account t <where> exists（select t2.invite_code from user_account t2 where" +
            "t2.id in(" +
            "<foreach collection='firstusers' item='id' separator=','>#{id}</foreach>)" +
            " and t.be_invite_code=t2.invite_code"+
            ")" +
            "</where></script>")
    List<Integer> getAllUserByfirstUsers(@Param("firstusers") List<Integer> firstusers);

    @UpdateProvider(type = UserBeanSqlProvider.class,method = "updateUser")
    int updateUser(@Param("userBean") UserBean userBean,Level... levels);

    @Select("select * from user_account where status='DISABLE'")
    List<UserBean> listBlackUsers(IPage<UserBean> userBeanIPage);

    @Update("update user_account set status='ENABLE' where id=#{userid}")
    int relieveBlack(Integer userid);

}
