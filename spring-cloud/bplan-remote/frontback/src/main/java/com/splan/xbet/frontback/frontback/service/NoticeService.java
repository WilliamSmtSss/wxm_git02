package com.splan.xbet.frontback.frontback.service;

import com.splan.base.bean.back.BackNotice;
import com.splan.base.http.CommonResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public interface NoticeService {

    CommonResult add(@RequestBody Map<String,Object> requestParam);

    CommonResult edit(@RequestBody Map<String,Object> requestParam);

    CommonResult del(@RequestBody Map<String,Object> requestParam);

    CommonResult<Page<BackNotice>> sel(@RequestBody Map<String,Object> requestParam);

}
