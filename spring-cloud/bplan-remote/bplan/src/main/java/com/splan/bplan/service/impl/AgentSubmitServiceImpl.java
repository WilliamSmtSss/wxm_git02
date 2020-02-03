package com.splan.bplan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.splan.base.bean.AgentSubmit;
import com.splan.base.bean.UserBean;
import com.splan.base.enums.ContactType;
import com.splan.bplan.mappers.AgentSubmitMapper;
import com.splan.bplan.service.IAgentSubmitService;
import org.springframework.stereotype.Service;

@Service
public class AgentSubmitServiceImpl extends ServiceImpl<AgentSubmitMapper, AgentSubmit> implements IAgentSubmitService {


    @Override
    public AgentSubmit submit(UserBean userBean,String qq) {
        QueryWrapper<AgentSubmit> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("qq",qq);
        AgentSubmit agentSubmit = getOne(queryWrapper);
        if (agentSubmit==null){
            agentSubmit = new AgentSubmit();
            agentSubmit.setQq(qq);
            agentSubmit.setUserId(userBean.getId());
            agentSubmit.setContact(ContactType.SUBMIT);
            save(agentSubmit);
        }
        return agentSubmit;
    }
}
