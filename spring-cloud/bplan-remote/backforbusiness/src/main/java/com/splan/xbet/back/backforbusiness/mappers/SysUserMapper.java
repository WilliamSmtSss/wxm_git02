package com.splan.xbet.back.backforbusiness.mappers;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.base.SuperMapper;
import com.splan.base.bean.front.SysUser;
import com.splan.base.enums.CheckStatus;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

public interface SysUserMapper extends SuperMapper<SysUser> {

    /**
     * 根据用户名和密码查询对应的用户
     */
    @Select("SELECT\n" +
            "            u.id       userId,\n" +
            "            u.username username,\n" +
            "            u.password password,\n" +
            "            u.nickname nickName,\n" +
            "            u.jurisdiction jurisdiction,\n" +
            "            u.api_id apiid,\n" +
            "            u.role_id as roleid\n" +
            "        FROM\n" +
            "            front_sys_user u\n" +
            "        WHERE u.username = #{username}\n" +
            "          AND u.password = #{password}\n" +
            "          AND u.delete_status = '1'")
    JSONObject getUser(@Param("username") String username, @Param("password") String password);

    /**
     * 修改密码
     */
    @Update("update front_sys_user set password =#{password} where username=#{username}")
    int modPassword(@Param("username") String username, @Param("password") String password);

    /**
     * 修改管理员信息
     */
    @UpdateProvider(type = SysUserSqlProvider.class,method = "updateive")
    int updatesysuserinfo(SysUser sysUser);

    /**
     * 联合角色查询
     */
    @Select("select t1.*,t2.role_name from front_sys_user t1,front_sys_role t2 where t1.role_id=t2.id where t1.fromid=#{userId}")
    List<SysUser> selectJoinRole(@Param("userId") String userId);

    //xBet
    @Select("<script>select t1.*,t2.role_name,ifnull(t3.username,'') as modify_name from front_sys_user t1 left join front_sys_role t2 on t1.role_id=t2.id left join front_sys_user t3 on t1.modify_id =t3.id<where> 1=1 " +
            "<if test='status!=null and status==\"1\"'> and t1.role_id=#{roleId} </if>"+
            "<if test='status!=null and status==\"0\"'> and t1.role_id=0 </if>"+
            "</where>" +
            "</script>")
    List<SysUser> getXbetSysUser(Page page, String roleId, String status);

    @Update("update front_sys_user t set t.role_id=#{roleId} where t.id=#{sysUserId}")
    int updateXbetSysUserRoleId(String sysUserId, String roleId);

    @Select("<script>select t1.*,t2.role_name,ifnull(t3.username,'') as from_name,ifnull(t4.username,'') as modify_name from front_sys_user t1 left join front_sys_role t2 on t1.role_id=t2.id left join front_sys_user t3 on t1.fromid=t3.id left join front_sys_user t4 on t1.modify_id=t4.id<where> 1=1 " +
            "<if test='status!=null '> and t1.delete_status=#{status} </if>"+
            "</where>" +
            "</script>")
    List<SysUser> getXBetSysUser2(Page page, String status);

    @Select("<script>select * from front_sys_user t left join front_company_info t2 on t.id=t2.sys_id <where> 1=1 " +
            "<if test='searchTxt!=null '> and (t.api_id=#{searchTxt} or t.username=#{searchTxt}) </if>"+
            "<if test='checkStatus!=null '> and t2.check_status=#{checkStatus} </if>"+
            "<if test='status!=null '> and t.delete_status=#{status} </if>"+
            "</where>"+
            "</script>")
    List<SysUser> getBusList(Page page, String searchTxt, CheckStatus checkStatus,Integer status);

}