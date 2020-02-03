package com.splan.data.datacenter.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.base.SuperMapper;
import com.splan.base.bean.UserBean;
import com.splan.base.enums.Level;
import com.splan.base.enums.Status;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

public interface UserMapper extends SuperMapper<UserBean> {

}
