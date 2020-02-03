package com.splan.xbet.back.backforbusiness.controller;

import com.splan.base.http.CommonResult;
import com.splan.xbet.back.backforbusiness.service.CommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/front/common")
@Api(tags={"公共模块"})
public class CommonController {

    @Autowired
    private CommonService commonService;

    @ApiOperation(value = "上传图片")
    @PostMapping("/upload")
    public CommonResult<String> upload(MultipartFile file){
        return commonService.upload(file);
    }

}
