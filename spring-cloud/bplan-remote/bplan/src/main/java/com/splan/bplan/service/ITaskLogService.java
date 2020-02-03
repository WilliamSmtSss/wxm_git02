package com.splan.bplan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.splan.base.bean.TaskLog;

public interface ITaskLogService extends IService<TaskLog> {

    void settleUserBonus();

    void monthBonus();
}
