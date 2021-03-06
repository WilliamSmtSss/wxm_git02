package com.splan.xbet.admin.back.mappers;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.xbet.admin.back.base.SuperMapper;
import com.splan.base.bean.SysRole;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysRoleMapper extends SuperMapper<SysRole> {
    @InsertProvider(type = SysRoleSqlProvider.class,method = "insertSelective")
    int insert(SysRole sysRole);

    @Select("select t.role_name from sys_role t where exists(select * from sys_user t2 where t.id=t2.role_id and t2.id=#{userId})")
    SysRole getRoleBySysuserId(String userId);

    //xBet
    @Select("select t1.*,ifnull(t2.username,'') as create_name,ifnull(t3.username,'') as modify_name from sys_role t1 left join sys_user t2 on t1.create_id=t2.id left join sys_user t3 on t1.modify_id=t3.id")
    List<SysRole> getXbetRole(Page page);
}