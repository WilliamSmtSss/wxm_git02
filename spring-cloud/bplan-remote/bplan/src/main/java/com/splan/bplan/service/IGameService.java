package com.splan.bplan.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.splan.base.bean.BetTopicsBean;
import com.splan.bplan.http.CommonResult;
import com.splan.bplan.result.GameResult;
import com.splan.bplan.result.HotGameResult;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Map;

public interface IGameService {


    /**
     * 获取列表
     * @param matchType
     * @param page
     * @return
     */
    @Cacheable(value = "game", key = "'gamelist_'+#page.current.toString() + #matchType.toString() + #gameIds + #leagueIds + #startDate ", unless = "#result eq null")
    IPage<GameResult> getGameList(Integer matchType, String gameIds,String leagueIds, IPage<GameResult> page,String startDate);

    CommonResult<List<GameResult>> getChangeGameList(String gameIds,String leagueIds);

    CommonResult<List<BetTopicsBean>> getBetTopicsList(Integer campaignId,Integer dataId,String type);

    CommonResult<GameResult> getGameDetail(Integer dataId);

    CommonResult<GameResult> getGameDetailWebSocket(Integer dataId);

    CommonResult<List<HotGameResult>> hotGame();
}
