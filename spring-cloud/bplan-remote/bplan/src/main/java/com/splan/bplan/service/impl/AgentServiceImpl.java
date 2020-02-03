package com.splan.bplan.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.splan.bplan.http.CommonResult;
import com.splan.bplan.result.AgentPlayerResult;
import com.splan.bplan.result.GameResult;
import com.splan.bplan.service.IAgentService;
import org.springframework.stereotype.Service;

@Service
public class AgentServiceImpl implements IAgentService {


    @Override
    public CommonResult<IPage<AgentPlayerResult>> selectPlayerByType(IPage<GameResult> page,Integer type) {
        return null;
    }
}
