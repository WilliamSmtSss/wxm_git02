package com.splan.xbet.frontback.frontback.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.http.CommonResult;
import com.splan.base.param.backremote.BackProductOrderParam;
import com.splan.base.result.backremote.BackProductOrderResult;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ServiceOrderService {

    CommonResult<Page<List<BackProductOrderResult>>> list(@RequestBody BackProductOrderParam backProductOrderParam);

    CommonResult add(@RequestBody BackProductOrderParam backProductOrderParam);

    CommonResult edit(@RequestBody BackProductOrderParam backProductOrderParam);

    CommonResult del(@RequestBody BackProductOrderParam backProductOrderParam);

    CommonResult check(@RequestBody BackProductOrderParam backProductOrderParam);

}
