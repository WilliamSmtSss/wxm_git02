package com.splan.xbet.frontback.frontback.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.BusinessConfigBean;
import com.splan.base.bean.front.FrontCompanyInfo;
import com.splan.base.bean.front.SysUser;
import com.splan.base.http.CommonResult;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public interface AccessMessageService {

    CommonResult checkCompanyInfo(@RequestBody Map<String,Object> requestParam);

    CommonResult<Page<FrontCompanyInfo>> selCompanyInfo(@RequestBody Map<String,Object> requestParam);

    CommonResult<Page<BusinessConfigBean>> busSel(@RequestBody Map<String, Object> requestParam);

    CommonResult busEdit(@RequestBody Map<String, Object> requestParam);

    CommonResult busAdd(@RequestBody Map<String, Object> requestParam);

    CommonResult<Page<SysUser>> busInfoSel(@RequestBody Map<String,Object> requestParam);
}
