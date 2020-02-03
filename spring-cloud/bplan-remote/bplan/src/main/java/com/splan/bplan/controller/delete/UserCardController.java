//package com.splan.bplan.controller.api;
//
//import com.splan.bplan.annotation.Authorization;
//import com.splan.bplan.annotation.CurrentUser;
//import com.splan.base.bean.UserBean;
//import com.splan.base.bean.UserCardBean;
//import com.splan.bplan.dto.UserCardDto;
//import com.splan.bplan.http.CommonResult;
//import com.splan.bplan.service.IUserCardService;
//import com.splan.bplan.utils.ResultUtil;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import springfox.documentation.annotations.ApiIgnore;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.Valid;
//import java.util.List;
//
///**
// * Created by lyn on 2019/1/18.
// */
//@RestController
//@RequestMapping("/api/cards")
//@Api(value = "银行卡接口", tags = {"银行卡接口"})
//public class UserCardController extends BaseController {
//
//    @Autowired
//    private IUserCardService userCardService;
//
//    @PostMapping("")
//    @ApiOperation(value = "银行卡添加接口", notes = "")
//    @Authorization
//    public CommonResult<String> addCard (@Valid UserCardDto card, @CurrentUser @ApiIgnore UserBean user,
//                                         BindingResult bindingResult, HttpServletRequest request) {
//        CommonResult result = validParams(bindingResult);
//        if (!result.isSuccess()){
//            return result;
//        }
//        card.setUserId(user.getId());
//        return userCardService.addCard(card);
//    }
//
//    @GetMapping("")
//    @ApiOperation(value = "银行卡列表接口", notes = "")
//    @Authorization
//    public CommonResult<List<UserCardBean>> cardList (@CurrentUser @ApiIgnore UserBean user, HttpServletRequest request) {
//        return ResultUtil.returnSuccess(userCardService.getList(user.getId()));
//    }
//}
