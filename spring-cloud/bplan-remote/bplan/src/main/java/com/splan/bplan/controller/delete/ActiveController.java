//package com.splan.bplan.controller.api;
//
//import com.splan.bplan.annotation.Authorization;
//import com.splan.bplan.annotation.CurrentUser;
//import com.splan.base.bean.UserBean;
//import com.splan.base.enums.ResultStatus;
//import com.splan.base.enums.TriggerType;
//import com.splan.bplan.http.CommonResult;
//import com.splan.bplan.result.ActiveResult;
//import com.splan.bplan.result.SignResult;
//import com.splan.bplan.service.IActiveService;
//import com.splan.bplan.utils.ResultUtil;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import springfox.documentation.annotations.ApiIgnore;
//
//@RestController
//@RequestMapping("/api/active")
//@Api(value = "活跃度接口",tags = {"活跃度接口"})
//public class ActiveController {
//
//    @Autowired
//    private IActiveService activeService;
//
//
//    @GetMapping("/getActive")
//    @ApiOperation(value = "获取活跃度信息")
//    @Authorization
//    public CommonResult<ActiveResult> getActive(@CurrentUser @ApiIgnore UserBean userBean){
//        return ResultUtil.returnSuccess(activeService.getActivityList(userBean.getId()));
//
//    }
//
//    @GetMapping("/getReward")
//    @ApiOperation(value = "领取活跃奖励")
//    @Authorization
//    public CommonResult<String> getReward(@CurrentUser @ApiIgnore UserBean userBean,Integer activeId){
//        boolean flag = activeService.doActive(userBean.getId(),activeId, TriggerType.FRONT);
//        if (flag){
//            return ResultUtil.returnSuccess("");
//        }else {
//            return ResultUtil.returnError(ResultStatus.NO_TO_LIMIT);
//        }
//    }
//
//    @GetMapping("/sign")
//    @ApiOperation(value = "签到")
//    @Authorization
//    public CommonResult<SignResult> sign(@CurrentUser @ApiIgnore UserBean userBean){
//        SignResult flag = activeService.sign(userBean.getId());
//        if (flag.isFlag()){
//            return ResultUtil.returnSuccess(flag);
//        }else {
//            return ResultUtil.returnError(ResultStatus.NO_TO_LIMIT);
//        }
//    }
//
//    @GetMapping("/updateActive")
//    @ApiOperation(value = "修改活跃度")
//    @Authorization
//    public int updateActive(@CurrentUser @ApiIgnore UserBean userBean,Integer activeId,Integer val){
//        return activeService.updateActive(activeId,val);
//    }
//
//
//}
