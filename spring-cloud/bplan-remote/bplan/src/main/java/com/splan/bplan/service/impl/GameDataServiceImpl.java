package com.splan.bplan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.splan.base.bean.GameDataBean;
import com.splan.bplan.mappers.GameDataBeanMapper;
import com.splan.bplan.mappers.GameDataCacheMapper;
import com.splan.bplan.result.GameResult;
import com.splan.bplan.service.IGameDataService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GameDataServiceImpl extends ServiceImpl<GameDataBeanMapper, GameDataBean> implements IGameDataService {

    @Autowired
    private GameDataBeanMapper gameDataBeanMapper;

    @Autowired
    private GameDataCacheMapper gameDataCacheMapper;

    @Override
    public Map<String, Object> gameCount(String gameIds, String leagueIds,String startDate) {
        Map<String,Object> result = new HashMap<>();
        Integer day = gameDataCacheMapper.countGameResultBy24HoursAndPage(gameIds,leagueIds);
        result.put("day",day);
        Integer notStartYet = gameDataCacheMapper.countGameResultByBegin(gameIds,leagueIds,startDate);
        result.put("not_start_yet",notStartYet);
        Integer rolling = gameDataCacheMapper.countGameResultByRollingInt(gameIds,leagueIds);
        result.put("ongoing",rolling);

        return result;
    }

    @Override
    public GameResult selectGameResultWithTeamById(Integer dataId) {
        return gameDataBeanMapper.selectGameWithTeamCampainsResultById(dataId);
    }
}
