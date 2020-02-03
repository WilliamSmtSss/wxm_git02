package com.splan.bplan.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.BetTopicsBean;
import com.splan.base.bean.BusinessConfigBean;
import com.splan.base.bean.GameTypeBean;
import com.splan.bplan.http.CommonResult;
import com.splan.bplan.result.ConfigResult;
import com.splan.bplan.result.GameLeagueResult;
import com.splan.bplan.result.GameResult;
import com.splan.bplan.result.HotGameResult;
import com.splan.bplan.service.IBusinessConfigService;
import com.splan.bplan.service.IGameDataService;
import com.splan.bplan.service.IGameService;
import com.splan.bplan.utils.DateUtil;
import com.splan.bplan.utils.ResultUtil;
import com.splan.bplan.service.IGameTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/games")
@Api(value="赛程接口",tags={"赛程接口"})
public class GameController extends BaseController{

    @Autowired
    private IGameService gameService;

    @Autowired
    private IGameDataService gameDataService;

    @Autowired
    private IGameTypeService gameTypeService;

    @Autowired
    private IBusinessConfigService businessConfigService;

    @GetMapping("/list")
    @ApiOperation(value="游戏列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "matchType",value = "1. 24小时 2.滚盘 3.赛前 4.已结束",dataType = "Integer"),
            @ApiImplicitParam(name = "gameIds",value = "游戏类别id，以,分隔， dota2，csgo,(传空表示全选)",dataType = "String"),//,
            @ApiImplicitParam(name = "leagueIds",value = "联赛ids,(传空表示全选)",dataType = "String")}
            //@ApiImplicitParam(name = "page",value = "默认1",dataType = "Page")}
    )
    public CommonResult<IPage<GameResult>> game(Integer matchType, String gameIds,String leagueIds, @RequestParam(value="page", defaultValue="1") int currentPage,
                                                @RequestParam(defaultValue="10") int per,String startDate){

        if (matchType == null){
            matchType = 1;
        }
        Page page = new Page<>(currentPage,per);

        return ResultUtil.returnSuccess(gameService.getGameList(matchType,gameIds,leagueIds,page,startDate));

    }

    @GetMapping("/ongoinglist")
    @ApiOperation(value="切换正在进行中游戏列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gameIds",value = "游戏类别id，以,分隔， dota2，csgo,(传空表示全选)",dataType = "String"),//,
            @ApiImplicitParam(name = "leagueIds",value = "联赛ids,(传空表示全选)",dataType = "String")}
            //@ApiImplicitParam(name = "page",value = "默认1",dataType = "Page")}
    )
    public CommonResult<List<GameResult>> changeGameList(String gameIds,String leagueIds){
        return gameService.getChangeGameList(gameIds,leagueIds);

    }

    @GetMapping("/detail")
    @ApiOperation(value="比赛详情")
    public CommonResult<GameResult> game(Integer dataId){

        return gameService.getGameDetail(dataId);

    }

    @GetMapping("/betlist")
    @ApiOperation(value="盘口列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "campaignId", value = "场次id", dataType = "Integer"),
            @ApiImplicitParam(name = "dataId", value = "比赛dataId", dataType = "Integer"),
            @ApiImplicitParam(name = "type", value = "type=all的时候 获取全场，type=scene的时候获取单场次，allbet获取全部盘口，roll 滚球", dataType = "String")
    })
    public CommonResult<List<BetTopicsBean>> betList(Integer campaignId,Integer dataId,String type){

        return gameService.getBetTopicsList(campaignId,dataId,type);

    }

    @GetMapping("/typelist")
    @ApiOperation(value = "游戏类型列表")
    public CommonResult<List<GameTypeBean>> typeList() {
        return gameTypeService.getGameTypeList();
    }

    @GetMapping("/pctypelist")
    @ApiOperation(value = "pc游戏类型列表")
    public CommonResult<List<GameTypeBean>> pctypeList() {
        return gameTypeService.getPcGameTypeList();
    }

    @GetMapping("/leagueList")
    @ApiOperation(value = "联赛列表")
    public CommonResult<List<GameLeagueResult>> leagueList(Integer gameId) {
        return gameTypeService.leagueList(gameId);
    }


    @GetMapping("/gameTime")
    @ApiOperation(value="比赛日期")
    public CommonResult<List<String>> gameTime(){

        return ResultUtil.returnSuccess(DateUtil.getFeatureDateList(7));

    }


    @GetMapping("/gameCount")
    @ApiOperation(value="比赛统计")
    public CommonResult<Map<String,Object>> gameCount(String gameIds,String leagueIds,String startDate){

        return ResultUtil.returnSuccess(gameDataService.gameCount(gameIds,leagueIds,startDate));

    }

    @GetMapping("/hotGame")
    @ApiOperation(value="热门游戏")
    public CommonResult<List<HotGameResult>> hotGame(){
        return gameService.hotGame();

    }


    @GetMapping("/config")
    @ApiOperation(value="默认配置")
    public CommonResult<ConfigResult> config(String apiId){
        return ResultUtil.returnSuccess(businessConfigService.getByApiId(apiId));

    }
}
