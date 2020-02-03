package com.splan.bplan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.splan.base.bean.GameLeagueBean;
import com.splan.bplan.mappers.GameLeagueBeanMapper;
import com.splan.bplan.service.IGameLeagueService;
import org.springframework.stereotype.Service;

@Service
public class GameLeagueServiceImpl extends ServiceImpl<GameLeagueBeanMapper, GameLeagueBean> implements IGameLeagueService {
}
