package com.splan.bplan.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.AnnouncementBean;
import com.splan.base.bean.NoticeBean;
import com.splan.base.bean.UserBean;
import com.splan.bplan.annotation.Authorization;
import com.splan.bplan.annotation.CurrentUser;
import com.splan.bplan.http.CommonResult;
import com.splan.bplan.service.IAnnouncementService;
import com.splan.bplan.service.INoticeService;
import com.splan.bplan.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping(value = "/api/notice")
@Api(value = "公告接口", tags = {"消息接口"})
public class NoticeController {

    @Autowired
    private INoticeService noticeService;

    @Autowired
    private IAnnouncementService announcementService;

    @GetMapping("/list")
    @ApiOperation(value="公告列表",notes="")
    public CommonResult<List<AnnouncementBean>> list(String apiId){
       return ResultUtil.returnSuccess(announcementService.noticeList(apiId));
    }

    @GetMapping("/personlist")
    @ApiOperation(value="个人消息",notes="")
    @Authorization
    public CommonResult<IPage<NoticeBean>> personList(@CurrentUser @ApiIgnore UserBean userBean, @RequestParam(value="page", defaultValue="1") int currentPage,
                                                      @RequestParam(defaultValue="10") int per){
        Page page = new Page<>(currentPage,per);
        IPage<NoticeBean> pageList = noticeService.personNoticeList(page,userBean);
        if (pageList.getTotal()>0){
            noticeService.readAllMessage(userBean);
        }
        return ResultUtil.returnSuccess(pageList);
    }
}
