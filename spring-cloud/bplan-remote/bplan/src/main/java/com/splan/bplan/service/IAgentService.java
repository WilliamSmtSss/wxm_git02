package com.splan.bplan.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.splan.bplan.http.CommonResult;
import com.splan.bplan.result.AgentPlayerResult;
import com.splan.bplan.result.GameResult;

/**
 * 代理
 */
public interface IAgentService {

    CommonResult<IPage<AgentPlayerResult>> selectPlayerByType(IPage<GameResult> page,Integer type);
}
