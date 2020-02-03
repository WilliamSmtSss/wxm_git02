package com.splan.bplan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.splan.base.bean.GameTeamBean;
import com.splan.bplan.mappers.GameTeamBeanMapper;
import com.splan.bplan.service.IGameTeamService;
import org.springframework.stereotype.Service;

@Service
public class GameTeamServiceImpl extends ServiceImpl<GameTeamBeanMapper, GameTeamBean> implements IGameTeamService {
}
