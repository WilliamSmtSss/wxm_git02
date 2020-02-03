package com.splan.bplan.mappers;

import com.splan.base.base.SuperMapper;
import com.splan.base.bean.SysRole;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;

public interface SysRoleMapper extends SuperMapper<SysRole> {
    @InsertProvider(type = SysRoleSqlProvider.class,method = "insertSelective")
    int insert(SysRole sysRole);

}