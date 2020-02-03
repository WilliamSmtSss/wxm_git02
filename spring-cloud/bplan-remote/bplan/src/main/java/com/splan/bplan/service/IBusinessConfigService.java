package com.splan.bplan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.splan.base.bean.BusinessConfigBean;
import com.splan.bplan.http.CommonResult;
import com.splan.bplan.result.ConfigResult;

public interface IBusinessConfigService extends IService<BusinessConfigBean> {

    BusinessConfigBean selectByApiId(String apiId);

    ConfigResult getByApiId(String apiId);
}
