package com.splan.bplan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.splan.base.bean.CommonMobileAreaBean;
import com.splan.bplan.http.CommonResult;
import com.splan.bplan.mappers.CommonMobileAreaBeanMapper;
import com.splan.bplan.service.IMobileAreaService;
import com.splan.bplan.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lyn on 2019/1/19.
 */
@Service
public class MobileAreaServiceImpl extends ServiceImpl<CommonMobileAreaBeanMapper, CommonMobileAreaBean> implements IMobileAreaService {

    @Autowired
    private CommonMobileAreaBeanMapper mobileAreaBeanMapper;

    @Override
    public CommonResult<List<CommonMobileAreaBean>> getMobileAreaList() {
        List<CommonMobileAreaBean> areaList = mobileAreaBeanMapper.selectAreaList();
        return ResultUtil.returnSuccess(areaList);
    }
}
