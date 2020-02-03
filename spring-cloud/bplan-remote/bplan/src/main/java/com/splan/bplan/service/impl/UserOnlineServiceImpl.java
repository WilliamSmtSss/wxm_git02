package com.splan.bplan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.splan.base.bean.UserOnlineBean;
import com.splan.bplan.mappers.UserOnlineMapper;
import com.splan.bplan.service.IUserOnlineService;
import org.springframework.stereotype.Service;

@Service
public class UserOnlineServiceImpl extends ServiceImpl<UserOnlineMapper, UserOnlineBean> implements IUserOnlineService {

}
