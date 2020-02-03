package com.splan.bplan.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.NoticeBean;
import com.splan.base.bean.UserBean;
import com.splan.bplan.annotation.Authorization;
import com.splan.bplan.annotation.CurrentUser;
import com.splan.bplan.http.CommonResult;
import com.splan.bplan.result.UserResult;
import com.splan.bplan.service.IMessageService;
import com.splan.bplan.service.INoticeService;
import com.splan.bplan.service.IUserService;
import com.splan.bplan.service.TokenManager;
import com.splan.bplan.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/oauth/users")
@Api(value="用户登录",tags={"用户操作接口"})
public class UserController extends BaseController{

    @Autowired
    private IUserService userService;

    @Autowired
    private INoticeService noticeService;

    @Autowired
    private TokenManager tokenManager;


    @GetMapping("/getUserInfo")
    @ApiOperation(value="获取用户基础信息",notes="")
    @Authorization
    public CommonResult<UserResult> getUserInfo(@CurrentUser @ApiIgnore UserBean user){
        return userService.getUserInfo(user);
    }

    @GetMapping("/personlist")
    @ApiOperation(value="个人消息",notes="")
    @Authorization
    public CommonResult<IPage<NoticeBean>> personList(@CurrentUser @ApiIgnore UserBean userBean, @RequestParam(value="page", defaultValue="1") int currentPage,
                                                      @RequestParam(defaultValue="10") int per){
        Page page = new Page<>(currentPage,per);
        IPage<NoticeBean> pagelist = noticeService.personNoticeList(page,userBean);
        if (pagelist.getTotal()>0){
            noticeService.readAllMessage(userBean);
        }
        return ResultUtil.returnSuccess(pagelist);
    }

    @RequestMapping (value = "logout",method = RequestMethod.DELETE)
    @Authorization
    public CommonResult<String> logout (@CurrentUser @ApiIgnore UserBean user) {
        tokenManager.deleteToken (user.getId ());
        return ResultUtil.returnSuccess("");
    }

    @GetMapping("/getTest")
    @ApiOperation(value="test",notes="")
    public String getTest(){
        return "gan";
    }






}
