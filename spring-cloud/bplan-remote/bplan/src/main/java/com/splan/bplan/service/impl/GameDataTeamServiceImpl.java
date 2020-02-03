package com.splan.bplan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.splan.base.bean.GameDataTeamBean;
import com.splan.bplan.mappers.GameDataTeamBeanMapper;
import com.splan.bplan.service.IGameDataTeamService;
import org.springframework.stereotype.Service;

@Service
public class GameDataTeamServiceImpl extends ServiceImpl<GameDataTeamBeanMapper, GameDataTeamBean> implements IGameDataTeamService {
}
