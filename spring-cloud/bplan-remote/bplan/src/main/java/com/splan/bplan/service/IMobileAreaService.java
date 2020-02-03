package com.splan.bplan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.splan.base.bean.CommonMobileAreaBean;
import com.splan.bplan.http.CommonResult;

import java.util.List;

/**
 * Created by lyn on 2019/1/19.
 */
public interface IMobileAreaService extends IService<CommonMobileAreaBean> {
    CommonResult<List<CommonMobileAreaBean>> getMobileAreaList();
}
