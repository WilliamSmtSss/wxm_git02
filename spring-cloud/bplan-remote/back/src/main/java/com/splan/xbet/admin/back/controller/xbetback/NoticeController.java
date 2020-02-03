package com.splan.xbet.admin.back.controller.xbetback;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.BusinessConfigBean;
import com.splan.base.bean.AnnouncementBean;
import com.splan.base.bean.SysUser;
import com.splan.base.enums.NoticeAction;
import com.splan.base.enums.ResultStatus;
import com.splan.base.enums.Status;
import com.splan.base.http.CommonResult;
import com.splan.xbet.admin.back.annotation.CurrentSysUser;
import com.splan.xbet.admin.back.dto.DateDto;
import com.splan.xbet.admin.back.enums.BusRole;
import com.splan.xbet.admin.back.mappers.AnnouncementBeanMapper;
import com.splan.xbet.admin.back.mappers.BusinessConfigBeanMapper;
import com.splan.xbet.admin.back.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/back/xBet/notice")
@Api(tags={"xBet后台:消息管理"})
public class NoticeController {

    @Autowired
    private BusinessConfigBeanMapper businessConfigBeanMapper;

    @Autowired
    private AnnouncementBeanMapper announcementBeanMapper;

    @GetMapping("/list")
    @ApiOperation(value = "公告列表", notes = "")
    @RequiresPermissions(value = "noticeManage:notice")
    public CommonResult<IPage<AnnouncementBean>> list(@CurrentSysUser @ApiIgnore SysUser sysUser, Page page, DateDto dateDto) {
        List<AnnouncementBean> noticeBeanList=announcementBeanMapper.getNotices(page,sysUser.getApiid(),dateDto.getStartDate(),dateDto.getEndDate(),NoticeAction.notice);
//        noticeBeanList.forEach(noticeBean -> {
//            noticeBean.setContent("\"" + CommonUtil.string2Json(noticeBean.getContent())+"\"");
//        });
        page.setRecords(noticeBeanList);
        return ResultUtil.returnSuccess(page);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除公告", notes = "")
    @RequiresPermissions(value = "noticeManage:notice")
    public CommonResult<Integer> delete(Integer id) {
        AnnouncementBean notice = announcementBeanMapper.selectById(id);
        notice.setStatus(Status.DISABLE);
        notice.setUpdateTime(new Date());
        return ResultUtil.returnSuccess(announcementBeanMapper.updateById(notice));
    }

    @PostMapping("/add")
    @ApiOperation(value = "添加公告", notes = "")
    @RequiresPermissions(value = "noticeManage:notice")
    public CommonResult<Integer> add(@CurrentSysUser @ApiIgnore SysUser sysUser,AnnouncementBean noticeBean,String bigBusiness, String smallBusiness) {
        if (null != noticeBean.getId()) {
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        }
        if(null==noticeBean.getTitle()||"".equals(noticeBean.getTitle().trim())){
            return ResultUtil.returnError(ResultStatus.PARAM_EMPTY);
        }
//        JSONObject sysUser = (JSONObject) SecurityUtils.getSubject().getPrincipal();
//        if (StringUtils.isBlank(sysUser.getString("apiid"))){
//            noticeBean.setApiId(null);
//        }else {
//            noticeBean.setApiId(sysUser.getString("apiid"));
//        }
//        if(StringUtils.isBlank(bigBusiness) && StringUtils.isBlank(smallBusiness))
//            return ResultUtil.returnError(ResultStatus.PARAM_EMPTY);

        int x=0;
        if (sysUser.getRoleId() == BusRole.BUS.geteName()) {
            bigBusiness=sysUser.getApiid();
        }else if(sysUser.getRoleId() == BusRole.BUS_DOWN.geteName()){
            smallBusiness=sysUser.getApiid();
        }
        if(StringUtils.isNotBlank(smallBusiness)){
            noticeBean.setApiId(smallBusiness);
            noticeBean.setAction(NoticeAction.notice);
            noticeBean.setPApiId(bigBusiness);
            noticeBean.setCreateName(sysUser.getUsername());
            x=announcementBeanMapper.insert(noticeBean);
        }else if(StringUtils.isNotBlank(bigBusiness)){
            List<BusinessConfigBean> list=businessConfigBeanMapper.getListByapiId(bigBusiness);
            for(BusinessConfigBean businessConfigBean:list){
                noticeBean.setApiId(businessConfigBean.getApiId());
                noticeBean.setAction(NoticeAction.notice);
                noticeBean.setPApiId(bigBusiness);
                noticeBean.setCreateName(sysUser.getUsername());
                x=announcementBeanMapper.insert(noticeBean);
            };
        }else{
            noticeBean.setAction(NoticeAction.notice);
            noticeBean.setCreateName(sysUser.getUsername());
            x=announcementBeanMapper.insert(noticeBean);
        }

        return ResultUtil.returnSuccess(x);
    }

    @PostMapping("/update")
    @ApiOperation(value = "编辑公告", notes = "")
    @RequiresPermissions(value = "noticeManage:notice")
    public CommonResult<Integer> update(AnnouncementBean noticeBean) {
        if (null == noticeBean.getId()) {
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
        }
        return ResultUtil.returnSuccess(announcementBeanMapper.updateById(noticeBean));
    }

    @GetMapping("/betList")
    @ApiOperation(value = "消息列表", notes = "")
    @RequiresPermissions(value = "noticeManage:bet")
    public CommonResult<IPage<AnnouncementBean>> betList(@CurrentSysUser @ApiIgnore SysUser sysUser,Page page) {
        page.setRecords(announcementBeanMapper.getNotices(page,sysUser.getApiid(),null,null,NoticeAction.bet_notice));
        return ResultUtil.returnSuccess(page);
    }

}
