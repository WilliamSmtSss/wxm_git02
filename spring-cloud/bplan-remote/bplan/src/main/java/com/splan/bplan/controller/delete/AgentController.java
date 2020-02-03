//package com.splan.bplan.controller.api;
//
//import com.splan.bplan.annotation.Authorization;
//import com.splan.bplan.annotation.CurrentUser;
//import com.splan.base.bean.UserBean;
//import com.splan.base.enums.Level;
//import com.splan.base.enums.ResultStatus;
//import com.splan.bplan.http.CommonResult;
//import com.splan.bplan.service.IAgentSubmitService;
//import com.splan.bplan.utils.ResultUtil;
//import com.splan.bplan.utils.ValidatorUtil;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import springfox.documentation.annotations.ApiIgnore;
//
//import javax.validation.constraints.Digits;
//
//@RestController
//@RequestMapping("/api/agent")
//@Api(value="代理接口",tags={"代理"})
//@Slf4j
//public class AgentController extends BaseController{
//
//    @Autowired
//    private IAgentSubmitService agentSubmitService;
//
//    @GetMapping("/detail")
//    @ApiOperation(value="cc",notes="")
//    @Authorization(level = Level.Agent)
//    public String game(){
//
//        return "1";
//
//    }
//
//    @PostMapping("/becomeAgent")
//    @ApiOperation(value="成为代理")
//    @Authorization
//    public CommonResult<String> becomeAgent(@CurrentUser @ApiIgnore UserBean userBean, String qq){
//        boolean flag = StringUtils.isNumeric(qq);
//        if (flag){
//            agentSubmitService.submit(userBean,qq);
//        }else {
//            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
//        }
//        return ResultUtil.returnSuccess(qq);
//    }
//}
