//package com.splan.bplan.controller.api;
//
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.splan.base.bean.CommonBankBean;
//import com.splan.bplan.http.CommonResult;
//import com.splan.bplan.result.BankResult;
//import com.splan.bplan.service.IBankService;
//import com.splan.bplan.utils.ResultUtil;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * Created by lyn on 2019/1/18.
// */
//@RestController
//@RequestMapping("/api/banks")
//@Api(value = "银行接口",tags = {"通用接口"})
//public class BankController extends BaseController {
//
//    @Autowired
//    private IBankService bankService;
//
//    @GetMapping("/list")
//    @ApiOperation(value = "可选银行列表",notes = "")
//    public CommonResult<IPage<BankResult>> bank(@RequestParam(value="page", defaultValue="1") int currentPage,
//                                                @RequestParam(defaultValue="10") int per) {
//        Page page = new Page<>(currentPage,per);
//        return bankService.getBankList(page);
//    }
//}
