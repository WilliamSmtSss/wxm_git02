package com.splan.xbet.frontback.frontback.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.back.BackProductInfo;
import com.splan.base.http.CommonResult;
import com.splan.base.param.backremote.BackProductInfoParam;
import com.splan.base.result.backremote.BackProductInfoResult;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ServiceManagerService {

    CommonResult<Page<List<BackProductInfoResult>>> list(@RequestBody BackProductInfoParam backProductInfoParam);

    CommonResult add( BackProductInfoParam backProductInfoParam);

    CommonResult edit( BackProductInfoParam backProductInfoParam);

    CommonResult del( BackProductInfoParam backProductInfoParam);
}
