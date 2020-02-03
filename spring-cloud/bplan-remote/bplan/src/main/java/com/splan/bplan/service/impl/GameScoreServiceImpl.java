package com.splan.bplan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.splan.base.bean.GameScoreBean;
import com.splan.bplan.mappers.GameScoreBeanMapper;
import com.splan.bplan.service.IGameScoreService;
import org.springframework.stereotype.Service;

@Service
public class GameScoreServiceImpl extends ServiceImpl<GameScoreBeanMapper, GameScoreBean> implements IGameScoreService {
}
