package com.splan.bplan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.splan.base.bean.ActivityListBean;
import com.splan.bplan.mappers.ActivityListBeanMapper;
import com.splan.bplan.service.IWelfareCenService;
import org.springframework.stereotype.Service;

@Service
public class WelfareCenServiceImpl extends ServiceImpl<ActivityListBeanMapper, ActivityListBean> implements IWelfareCenService {

}
