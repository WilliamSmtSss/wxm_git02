package com.splan.xbet.frontback.frontback.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.back.BackMessage;
import com.splan.base.http.CommonResult;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public interface MessageService {

    CommonResult<Page<BackMessage>> list(@RequestBody Map<String,Object> requestParam);

}
