package com.splan.bplan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.splan.base.bean.GameTypeBean;
import com.splan.bplan.http.CommonResult;
import com.splan.bplan.mappers.GameLeagueBeanMapper;
import com.splan.bplan.mappers.GameTypeBeanMapper;
import com.splan.bplan.result.GameLeagueResult;
import com.splan.bplan.service.IGameTypeService;
import com.splan.bplan.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameTypeServiceImpl extends ServiceImpl<GameTypeBeanMapper, GameTypeBean> implements IGameTypeService {

    @Autowired
    private GameTypeBeanMapper gameTypeBeanMapper;

    @Autowired
    private GameLeagueBeanMapper gameLeagueBeanMapper;

    @Override
    public CommonResult<List<GameTypeBean>> getGameTypeList() {
        return ResultUtil.returnSuccess(gameTypeBeanMapper.selectGameTypeList());
    }

    @Override
    public CommonResult<List<GameTypeBean>> getPcGameTypeList() {
        return ResultUtil.returnSuccess(gameTypeBeanMapper.selectPcGameTypeList());
    }

    @Override
    public CommonResult<List<GameLeagueResult>> leagueList(Integer gameId) {
        return ResultUtil.returnSuccess(gameLeagueBeanMapper.selectLeagueList(gameId));
    }
}
