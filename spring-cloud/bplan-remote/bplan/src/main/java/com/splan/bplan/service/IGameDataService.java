package com.splan.bplan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.splan.base.bean.GameDataBean;
import com.splan.bplan.result.GameResult;

import java.util.Map;

public interface IGameDataService extends IService<GameDataBean> {

    Map<String,Object> gameCount(String gameIds, String leagueIds,String startDate);

    GameResult selectGameResultWithTeamById(Integer dataId);
}
