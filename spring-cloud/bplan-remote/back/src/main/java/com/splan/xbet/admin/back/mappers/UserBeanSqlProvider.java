package com.splan.xbet.admin.back.mappers;

import com.splan.base.bean.UserBean;
import com.splan.base.enums.Level;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public class UserBeanSqlProvider {
    public String updateUser(UserBean userBean, Level... levels){
         SQL sql=new SQL();
         sql.UPDATE("user_account");
         if(userBean.getUsername()!=null)
           sql.SET("username=#{userBean.username,jdbcType=VARCHAR}");
         if(userBean.getStatus()!=null)
             sql.SET("status=#{userBean.status,jdbcType=VARCHAR}");
         if(userBean.getRealName()!=null)
             sql.SET("real_name=#{userBean,realName,jdbcType=VARCHAR}");
//         String wheresql="";
//         if (levels.size()==0) {
//             wheresql = "id=#{userBean.id}";
//         }
//         else{
//             wheresql = "id=#{userBean.id}";
//            for (Level level:levels){
//                wheresql+=" and userBean.level";
//            }
//         }
         sql.WHERE("id=#{userBean.id,jdbcType=BIGINT}");
         return sql.toString();
    }
}
