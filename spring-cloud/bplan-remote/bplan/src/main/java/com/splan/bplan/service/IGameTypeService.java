package com.splan.bplan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.splan.base.bean.GameTypeBean;
import com.splan.bplan.http.CommonResult;
import com.splan.bplan.result.GameLeagueResult;

import java.util.List;

public interface IGameTypeService extends IService<GameTypeBean> {
    CommonResult<List<GameTypeBean>> getGameTypeList();

    CommonResult<List<GameTypeBean>> getPcGameTypeList();

    CommonResult<List<GameLeagueResult>> leagueList(Integer gameId);
}
