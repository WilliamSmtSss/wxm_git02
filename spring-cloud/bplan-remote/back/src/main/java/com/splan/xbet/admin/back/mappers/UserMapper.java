package com.splan.xbet.admin.back.mappers;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.xbet.admin.back.base.SuperMapper;
import com.splan.base.bean.UserBean;
import com.splan.base.enums.Level;
import com.splan.base.enums.Status;
import com.splan.xbet.admin.back.result.MemberResult;
import com.splan.xbet.admin.back.result.UserAccountResult;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

public interface UserMapper extends SuperMapper<UserBean> {

    @Select("select * from user_account where username=#{username}")
    UserBean findByUsername(String username);

    @Select("select * from user_account where extra_id=#{username}")
    UserBean findByUsername2(String username);

    @Select("select * from user_account where invite_code=#{inviteCode}")
    UserBean findByInviteCode(String inviteCode);

    @Select("select * from user_account where mobile_area=#{mobileArea} and mobile=#{mobile}")
    UserBean findByMobile(String mobileArea, String mobile);

    @Select("select * from user_account where extra_id=#{extraId} and api_id=#{apiId}")
    UserBean findByApiIdAndExtraId(String apiId, String extraId);

    @Select("<script> select a.*,b.* from user_account as a left join user_balance as b on a.id=b.user_id <where> 1=1  " +
            " <if test=\"userId != null \"> AND a.id =#{userId} </if> "+
            " <if test=\"mobile != null and mobile !='' \"> AND a.mobile=#{mobile} </if> "+
            " <if test='levels != null '> AND level in (" +
            " <foreach collection='levels' item='level' separator=','> #{level}" +
            " </foreach>) </if> "+
            "</where> </script>")
    @Results({ @Result(column = "invite_code",property = "agentids",many = @Many(select = "com.splan.xbet.admin.back.mappers.UserMapper.getAllUsersByCode")),
//            @Result(column = "invite_code",property = "normals",many = @Many(select = "com.splan.xbet.admin.back.mappers.UserMapper.getNormalUsersByCode")),
    })
    List<UserAccountResult> selectAll(IPage<UserAccountResult> UserBean, Long userId, String mobile, List<Level> levels);

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
            @Result(column = "id",property = "userBalanceBean",many = @Many(select = "com.splan.xbet.admin.back.mappers.UserBalanceBeanMapper.selectUserBalanceByUserId")),
            @Result(column = "id",property = "ordertotle",many = @Many(select = "com.splan.xbet.admin.back.mappers.BetOrderBeanMapper.getTotleOrderByuserId")),
            @Result(column = "id",property = "rewardtotle",many = @Many(select = "com.splan.xbet.admin.back.mappers.BetOrderBeanMapper.getTotleRewardByuserId")),
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
            @Result(column = "id",property = "userBalanceBean",many = @Many(select = "com.splan.xbet.admin.back.mappers.UserBalanceBeanMapper.selectUserBalanceByUserId")),
            @Result(column = "id",property = "ordertotle",many = @Many(select = "com.splan.xbet.admin.back.mappers.BetOrderBeanMapper.getTotleOrderByuserId")),
            @Result(column = "id",property = "rewardtotle",many = @Many(select = "com.splan.xbet.admin.back.mappers.BetOrderBeanMapper.getTotleRewardByuserId")),
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
    int updateUser(@Param("userBean") UserBean userBean, Level... levels);

    @Select("select * from user_account where status='DISABLE'")
    List<UserBean> listBlackUsers(IPage<UserBean> userBeanIPage);

    @Update("update user_account set status='ENABLE' where id=#{userid}")
    int relieveBlack(Integer userid);

    //风暴娱乐后台
        //查询某日登陆用户列表
    @Select("<script>select t.id from user_account t <where> date_format(t.last_login_time,'%Y-%m-%d')=#{timeStr}<if test='registerChannel!=null'> and t.register_channel=#{registerChannel}</if></where></script>")
    List<Integer> getLoginByDay(String timeStr, String registerChannel);

        //查询某日注册用户数列表
    @Select("<script>select t.id from user_account t <where> date_format(t.create_time,'%Y-%m-%d')=#{timeStr}<if test='registerChannel!=null'> and t.register_channel=#{registerChannel}</if></where></script>")
    List<Integer> getRegisterByDay(String timeStr, String registerChannel);

    //查询某日某渠道注册用户数列表
    @Select("select t.id from user_account t where date_format(t.create_time,'%Y-%m-%d')=#{timeStr} and t.api_id=#{channel}")
    List<Integer> getRegisterByDayChannel(String timeStr, String channel);

    //查询某段时间某渠道的注册用户列表
    @Select("<script>" +
            "select id from user_account t <where> 1=1 " +
            "<if test='startDate!=null'> and t.create_time <![CDATA[>=]]> #{startDate} </if>" +
            "<if test='endDate!=null'> and t.create_time <![CDATA[<=]]> #{endDate} </if>" +
            "<if test='channel!=null'> and t.api_id=#{channel}</if>" +
            "</where>" +
            "</script>")
    List<Integer> getUserCount(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("channel") String channel);

    //查询某渠道的注册用户列表
    @Select("<script>" +
            "select id from user_account t <where> 1=1 " +
            "<if test='channel!=null'> and t.channel=#{channel}</if>" +
            "</where>" +
            "</script>")
    List<Integer> getUserIdsByChannel(@Param("channel") String channel);

    //用于统计用户留存率
    @Select("select count(1) from ( select * from user_account t where to_days(t.last_login_time)=to_days(#{date}) and t.api_id=#{channel} and exists (select * from user_account t2 where to_days(t2.create_time)=to_days(#{day}) and t.id=t2.id and t.api_id=t2.api_id) ) t3 \n")
    Integer getRemainUserCount(@Param("date") Date date, @Param("day") Date day, String channel);

    //用户中心
    @Select("<script>" +
            "select * from user_account t <where> 1=1 " +
            "<if test='startDate!=null'> and to_days(t.create_time)<![CDATA[>=]]>to_days(#{startDate})</if>" +
            "<if test='endDate!=null'> and to_days(t.create_time)<![CDATA[<=]]>to_days(#{endDate})</if>" +
            "<if test='userIdOrPhoneNum!=null and isPhone==\"0\"'> and t.invite_code=#{userIdOrPhoneNum} </if>" +
            "<if test='userIdOrPhoneNum!=null and isPhone==\"1\"'> and t.mobile=#{userIdOrPhoneNum} </if>" +
            "<if test='userIp!=null'> and t.register_ip=#{userIp} </if>" +
            " order by t.create_time desc "+
            "</where>" +
            "</script>")
    List<UserBean> userList(Page page, Date startDate, Date endDate, String userIdOrPhoneNum, String isPhone, String userIp);

    //冻结解冻用户
    @Update("update user_account set status=#{status} where id=#{userId}")
    int modUserStatus(String userId, Status status);

    @Select("select id from user_account t where to_days(t.last_login_time)=to_days(#{nowTime})")
    List<Integer> getTodayLoginUserIds(String nowTime);
    //查询所有平台
    @Select("select t.register_channel from user_account t group by t.register_channel")
    List<String> registerChannelList();

    //查询所有渠道
    @Select("select * from ( select t.channel from user_account t group by t.channel ) t where t.channel is not null and t.channel!=''")
    List<String> ChannelList();

    //邀请好友
    @Select("select * from user_account t where t.be_invite_code=#{inviteCode}")
    List<UserBean> getInviteList(Page page, String inviteCode);

    @Select("<script>select * from user_account t <where> t.`status`='ENABLE' and t.`level` in('Agent','SuperAgent')" +
            "<if test='userId!=null'> and t.invite_code=#{userId}</if>"+
            "</where>" +
            "</script>")
    List<UserBean> getAgentList(Page page, String userId);

    @Select("<script>" +
            "select count(1) from (" +
            "select * from user_account t where t.be_invite_code =#{inviteCode} and t.`status`='ENABLE' and TO_DAYS(NOW())-TO_DAYS(t.last_login_time)<![CDATA[<=]]>7 " +
            ") t"+
            "</script>")
    Integer getActiveUserCount(String inviteCode);

    @Select("<script>" +
            "select * from user_account t where t.be_invite_code =#{inviteCode} and t.`status`='ENABLE'" +
            "</script>")
    List<UserBean> getAgentDownUserList(Page page, String inviteCode);

    @Select("select count(*) from user_account t where t.register_ip=#{ip}")
    Integer countByIp(String ip);

    @Select("<script>" +
            "select t.*,ifnull(t2.c,0) as invite_count from user_account t LEFT JOIN (select t.be_invite_code,count(1) as c from user_account t GROUP BY t.be_invite_code) t2 on t.invite_code=t2.be_invite_code <where> 1=1 " +
            "<if test='startDate!=null'> and to_days(t.create_time)<![CDATA[>=]]>to_days(#{startDate})</if>" +
            "<if test='endDate!=null'> and to_days(t.create_time)<![CDATA[<=]]>to_days(#{endDate})</if>" +
            "<if test='inviteCode!=null'> and t.invite_code=#{inviteCode}</if>" +
            "<if test='inviteCodeCount!=null'> and t2.c<![CDATA[>=]]>${inviteCodeCount}</if>" +
            "</where>"+
            "</script>")
    List<UserBean> getInviteListBack(Page page, Date startDate, Date endDate, String inviteCode, String inviteCodeCount);

    @Select("<script>" +
            "select t.* from user_account t  <where> 1=1 " +
            "<if test='startDate!=null'> and to_days(t.create_time)<![CDATA[>=]]>to_days(#{startDate})</if>" +
            "<if test='endDate!=null'> and to_days(t.create_time)<![CDATA[<=]]>to_days(#{endDate})</if>" +
            "<if test='beforeInviteCode!=null'> and t.be_invite_code=#{beforeInviteCode}</if>" +
            "<if test='inviteCode!=null'> and t.invite_code=#{inviteCode}</if>" +
            "</where>"+
            "</script>")
    List<UserBean> getInviteDownListBack(Page page, Date startDate, Date endDate, String beforeInviteCode, String inviteCode);

}
