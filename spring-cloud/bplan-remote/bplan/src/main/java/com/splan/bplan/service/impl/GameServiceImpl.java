package com.splan.bplan.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.BetTopicsBean;
import com.splan.base.bean.GameDataBean;
import com.splan.base.bean.SysParamBean;
import com.splan.bplan.constants.SysParamConstants;
import com.splan.bplan.http.CommonResult;
import com.splan.bplan.mappers.*;
import com.splan.bplan.result.GameResult;
import com.splan.bplan.result.HotGameResult;
import com.splan.bplan.service.IGameService;
import com.splan.bplan.utils.ResultUtil;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GameServiceImpl implements IGameService {


    @Autowired
    private GameDataBeanMapper gameDataBeanMapper;

    @Autowired
    private GameDataCacheMapper gameDataCacheMapper;

    @Autowired
    private GameLeagueBeanMapper gameLeagueBeanMapper;

    @Autowired
    private BetTopicsBeanMapper betTopicsBeanMapper;

    @Autowired
    private SysParamBeanMapper sysParamBeanMapper;

    @Override
    public IPage<GameResult> getGameList(Integer matchType, String gameIds,String leagueIds, IPage<GameResult> page,String startDate) {
        List<GameResult> gameResultList = null;
        switch (matchType){
            /*case 1: gameResultList = gameDataBeanMapper.selectGameResultBy24HoursAndPage(page,gameIds,leagueIds);//24小时
            break;
            case 2: gameResultList = gameDataBeanMapper.selectGameResultByRolling(page,"ongoing",gameIds,startDate,leagueIds);//滚盘
            break;*/
            case 1: gameResultList = gameDataBeanMapper.selectGameResultBy24HoursAndPage(page,gameIds,leagueIds);//24小时
                break;
            case 2: gameResultList = gameDataBeanMapper.selectGameResultByRolling(page,gameIds,startDate,leagueIds);//滚盘
                break;
            case 3: gameResultList = gameDataBeanMapper.selectGameResultByNotStart(page,"not_start_yet",gameIds,startDate,leagueIds);//赛前
                break;
            case 4: gameResultList = gameDataBeanMapper.selectGameResultByEnd(page,"finished",gameIds,startDate,leagueIds);//结束
        }
        page.setRecords(gameResultList);
        return page;
    }

    @Override
    public CommonResult<List<GameResult>> getChangeGameList(String gameIds, String leagueIds) {
        List<GameResult> gameResultList = gameDataBeanMapper.selectGameResultSimpleByRolling("ongoing",gameIds,leagueIds);//滚盘
        return ResultUtil.returnSuccess(gameResultList);
    }

    @Override
    public CommonResult<List<BetTopicsBean>> getBetTopicsList(Integer campaignId,Integer dataId,String type) {
        if (StringUtils.isEmpty(type)){
            type = "all";
        }
        GameDataBean gameDataBean = gameDataBeanMapper.selectById(dataId);
        List<BetTopicsBean> betTopicsBeans = new ArrayList<>();
        if (!gameDataBean.getStatus().equals("finished")){
            if ("all".equals(type)){
                //betTopicsBeans = betTopicsBeanMapper.selectAllByDataId(dataId);
                betTopicsBeans = betTopicsBeanMapper.selectSeriByTopicableId(dataId);
            }else if ("allbet".equals(type)){
                betTopicsBeans = betTopicsBeanMapper.selectAllByDataId(dataId);
                //betTopicsBeans = betTopicsBeanMapper.selectSeriByTopicableId(dataId);
            }else if("roll".equals(type)){
                betTopicsBeans = betTopicsBeanMapper.selectRollingByTopicableId(dataId);
            }else {
                betTopicsBeans = betTopicsBeanMapper.selectByTopicableId(campaignId);
            }
        }else {
            if ("all".equals(type)){
                betTopicsBeans = betTopicsBeanMapper.selectListSeriesEndByDataId(dataId);
            }else {
                betTopicsBeans = betTopicsBeanMapper.selectListEndByTopicableId(campaignId);
            }
        }

        return ResultUtil.returnSuccess(betTopicsBeans);
    }

    @Override
    public CommonResult<GameResult> getGameDetail(Integer dataId) {
        GameResult gameResult = gameDataBeanMapper.selectGameResultById(dataId);
        if (gameResult.getRollingBall()==1){
            gameResult.setRollingStatus("yes");
        }
        return ResultUtil.returnSuccess(gameResult);
    }

    @Override
    public CommonResult<GameResult> getGameDetailWebSocket(Integer dataId) {
        GameResult gameResult = gameDataBeanMapper.selectGameResultWebSocketById(dataId);
        return ResultUtil.returnSuccess(gameResult);
    }

    @Override
    public CommonResult<List<HotGameResult>> hotGame() {
        SysParamBean sysParamBean = sysParamBeanMapper.selectByParamName(SysParamConstants.HOT_GAME);
        Integer hotGame = 123;
        if (sysParamBean!=null){
            hotGame = Integer.valueOf(sysParamBean.getParamValue());
        }
        List<GameResult> list = gameDataBeanMapper.selectGameResultByHotGame();
        List<HotGameResult> list1 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            HotGameResult hotGameResult = new HotGameResult();
            hotGameResult.setDataId(list.get(i).getId());
            if (list.get(i).getTeams()!=null && list.get(i).getTeams().size()>0){
                hotGameResult.setVs(list.get(i).getTeams().get(0).getAbbr()+" VS "+list.get(i).getTeams().get(1).getAbbr());
            }
            hotGameResult.setStartTime(list.get(i).getStartTime());
            if (list.get(i).getOrderCount()==0){
                hotGameResult.setOrderCount(RandomUtils.nextInt(1,10)*hotGame);
            }else {
                hotGameResult.setOrderCount(list.get(i).getOrderCount()*hotGame);
            }

            hotGameResult.setLeagueName(list.get(i).getLeague().getName());
            list1.add(hotGameResult);

        }
        return ResultUtil.returnSuccess(list1);
    }


}
