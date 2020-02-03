package com.splan.bplan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.splan.base.bean.BetOptionBean;

public interface IBetOptionService extends IService<BetOptionBean> {

    String getVsDetail(Integer betDataId);
}
