package com.splan.bplan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.splan.base.bean.AgentSubmit;
import com.splan.base.bean.UserBean;

public interface IAgentSubmitService extends IService<AgentSubmit> {

    AgentSubmit submit(UserBean userBean,String qq);
}
