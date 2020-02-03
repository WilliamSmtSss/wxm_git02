package com.splan.xbet.frontback.frontback.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.back.BackMessage;
import com.splan.base.http.CommonResult;
import com.splan.xbet.frontback.frontback.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/frontBack/Message")
@Api(tags={"消息通知"})
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status",value = "0:未处理 1：已处理"),
            @ApiImplicitParam(name = "msgType",value = "消息类型"),
            @ApiImplicitParam(name = "current",value = "当前页"),
            @ApiImplicitParam(name = "size",value = "条数"),
    })
    @ApiOperation("列表")
    public CommonResult<Page<BackMessage>> list(@RequestBody Map<String,Object> requestParam){
        return messageService.list(requestParam);
    }

}
