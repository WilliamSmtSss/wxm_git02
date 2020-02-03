package com.splan.xbet.frontback.frontback.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.http.CommonResult;
import com.splan.base.param.backremote.BackProductTrialParam;
import com.splan.base.result.backremote.BackProductTrialResult;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ServiceTrialService {

    CommonResult<Page<List<BackProductTrialResult>>> list(@RequestBody BackProductTrialParam backProductTrialParam);

    CommonResult add(@RequestBody BackProductTrialParam backProductTrialParam);

    CommonResult check(@RequestBody BackProductTrialParam backProductTrialParam);

    CommonResult del(@RequestBody BackProductTrialParam backProductTrialParam);

    CommonResult edit(@RequestBody BackProductTrialParam backProductTrialParam);

}
