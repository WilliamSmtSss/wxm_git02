//package com.splan.bplan.controller.api;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.splan.base.bean.CommonMobileAreaBean;
//import com.splan.bplan.http.CommonResult;
//import com.splan.bplan.service.IMobileAreaService;
//import com.splan.bplan.utils.MobileAreaCodeUtil;
//import com.splan.bplan.utils.ResultUtil;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
///**
// * Created by lyn on 2019/1/19.
// */
//@RestController
//@RequestMapping(value = "/api/mobileArea")
//@Api(value = "手机区号接口", tags = {"通用接口"})
//public class MobileAreaController {
//
//    @Autowired
//    private IMobileAreaService mobileAreaService;
//
//    @GetMapping("/list")
//    @ApiOperation(value="获取手机区号列表",notes="")
//    public CommonResult<List<CommonMobileAreaBean>> mobileArea() {
//        QueryWrapper<CommonMobileAreaBean> queryWrapper = new QueryWrapper<>();
//        queryWrapper.orderByAsc("area_order");
//        return ResultUtil.returnSuccess(mobileAreaService.list());
//    }
//
//    @GetMapping("/update")
//    @ApiOperation(value = "更新区号库", notes = "")
//    public CommonResult<String> update() {
//        List<CommonMobileAreaBean> list = MobileAreaCodeUtil.getAreaCode();
//        QueryWrapper<CommonMobileAreaBean> queryWrapper = new QueryWrapper<>();
//        mobileAreaService.remove(queryWrapper);
//        list.forEach(areaCode -> {
//            mobileAreaService.save(areaCode);
//        });
//        return ResultUtil.returnSuccess("");
//    }
//}
