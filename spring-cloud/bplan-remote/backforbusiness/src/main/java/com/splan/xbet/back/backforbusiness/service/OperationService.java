package com.splan.xbet.back.backforbusiness.service;

import com.splan.base.bean.front.SysUser;
import com.splan.base.param.backremote.BackProductOrderParam;
import com.splan.base.http.CommonResult;
import com.splan.base.param.frontremote.ParamConfigParam;
import com.splan.xbet.back.backforbusiness.param.AuthenticationEnterpriseParam;
import org.springframework.web.bind.annotation.RequestBody;

public interface OperationService {

    CommonResult openService(BackProductOrderParam backProductOrderParam);

    CommonResult paramConfig(@RequestBody ParamConfigParam paramConfigParam);

    CommonResult authenticationEnterprise(SysUser sysUser, @RequestBody AuthenticationEnterpriseParam authenticationEnterpriseParam);

    CommonResult editEnterprise(@RequestBody AuthenticationEnterpriseParam authenticationEnterpriseParam);

    CommonResult serviceTrialAdd(SysUser sysUser);

    CommonResult<SysUser> editAccountInfo(SysUser sysUser,String nickName);

}
