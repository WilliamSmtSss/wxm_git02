package com.splan.xbet.back.backforbusiness.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.splan.base.bean.back.BackNotice;
import com.splan.base.bean.front.FrontCompanyInfo;
import com.splan.base.bean.front.SysUser;
import com.splan.base.param.backremote.BackProductOrderParam;
import com.splan.base.result.backremote.BackProductTrialResult;
import com.splan.base.http.CommonResult;
import com.splan.base.param.remote.BusinessFormsParam;
import com.splan.base.param.remote.FirstDataParam;
import com.splan.base.result.BusinessFormsResult;
import com.splan.base.result.backremote.BackProductInfoResult;
import com.splan.base.result.remote.FrontDataResultOut;
import com.splan.xbet.back.backforbusiness.annotation.CurrentSysUser;
import org.springframework.web.bind.annotation.RequestBody;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

public interface DataService {

    CommonResult<FrontDataResultOut> getFirstData(@RequestBody FirstDataParam firstDataParam);

    CommonResult<List<BusinessFormsResult>> businessForms(@RequestBody BusinessFormsParam businessFormsParam);

    CommonResult<Page<BackProductInfoResult>> servicesList(BackProductOrderParam backProductOrderParam);

    CommonResult<FrontCompanyInfo> companyInfo( SysUser sysUser);

    CommonResult<BackProductTrialResult> serviceTrial(SysUser sysUser);

    CommonResult paramConfigSel(SysUser sysUser);

    CommonResult<Page<BackNotice>> noticeSel(@ApiIgnore @CurrentSysUser SysUser sysUser, @RequestBody Map<String,Object> req);

}
