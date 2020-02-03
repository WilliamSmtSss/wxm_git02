package com.splan.xbet.frontback.frontback.controller;

import com.splan.base.bean.back.BackNotice;
import com.splan.base.http.CommonResult;
import com.splan.xbet.frontback.frontback.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Map;

@RestController
@RequestMapping("/frontBack/Notice")
@Api(tags={"公告通知"})
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @PostMapping("/add")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title",value = "标题"),
            @ApiImplicitParam(name = "type",value = "公告类型"),
            @ApiImplicitParam(name = "product",value = "产品"),
            @ApiImplicitParam(name = "datatype",value = "数据类型 电竞：race 体育：sport"),
            @ApiImplicitParam(name = "name",value = "公告内容"),
            @ApiImplicitParam(name = "zdId",value = "置顶标识:1"),
            @ApiImplicitParam(name = "status",value = "发布状态"),
    })
    @ApiOperation("新增")
    public CommonResult add(@RequestBody Map<String,Object> requestParam){
        return noticeService.add(requestParam);
    }


    @PostMapping("/edit")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "ID"),
            @ApiImplicitParam(name = "title",value = "标题"),
            @ApiImplicitParam(name = "type",value = "公告类型"),
            @ApiImplicitParam(name = "product",value = "产品"),
            @ApiImplicitParam(name = "datatype",value = "数据类型 电竞：race 体育：sport"),
            @ApiImplicitParam(name = "name",value = "公告内容"),
            @ApiImplicitParam(name = "zdId",value = "置顶标识:1"),
            @ApiImplicitParam(name = "status",value = "发布状态"),
    })
    @ApiOperation("编辑")
    public CommonResult edit(@RequestBody Map<String,Object> requestParam){
        return noticeService.edit(requestParam);
    }

    @PostMapping("/del")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "公告ID"),
    })
    @ApiOperation("删除")
    public CommonResult del(@RequestBody Map<String,Object> requestParam){
        return noticeService.del(requestParam);
    }

    @PostMapping("/sel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type",value = "公告类型"),
            @ApiImplicitParam(name = "product",value = "产品"),
            @ApiImplicitParam(name = "status",value = "状态"),
            @ApiImplicitParam(name = "current",value = ""),
            @ApiImplicitParam(name = "size",value = ""),
    })
    @ApiOperation("列表")
    public CommonResult<Page<BackNotice>> sel(@RequestBody Map<String,Object> requestParam){
        return noticeService.sel(requestParam);
    }


}
