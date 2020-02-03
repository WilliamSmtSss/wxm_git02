package com.splan.xbet.admin.back.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.splan.base.bean.GameTypeBean;
import com.splan.base.http.CommonResult;
import com.splan.xbet.admin.back.mappers.GameLeagueBeanMapper;
import com.splan.xbet.admin.back.mappers.GameTypeBeanMapper;
import com.splan.xbet.admin.back.result.GameLeagueResult;
import com.splan.xbet.admin.back.service.IGameTypeService;
import com.splan.xbet.admin.back.utils.ResultUtil;
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
