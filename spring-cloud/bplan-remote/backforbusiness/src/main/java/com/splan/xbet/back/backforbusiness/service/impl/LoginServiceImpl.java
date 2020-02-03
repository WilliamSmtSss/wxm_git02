package com.splan.xbet.back.backforbusiness.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.splan.base.bean.front.FrontCompanyInfo;
import com.splan.base.bean.front.SysUser;
import com.splan.base.enums.ResultStatus;
import com.splan.base.enums.front.RegisterType;
import com.splan.base.http.CommonResult;
import com.splan.base.utils.ResultUtil;
import com.splan.xbet.back.backforbusiness.api.mail.MailService;
import com.splan.xbet.back.backforbusiness.api.sms.JavaSmsApi;
import com.splan.xbet.back.backforbusiness.contantes.*;
import com.splan.xbet.back.backforbusiness.mappers.FrontCompanyInfoMapper;
import com.splan.xbet.back.backforbusiness.mappers.SysRoleMapper;
import com.splan.xbet.back.backforbusiness.mappers.SysUserMapper;
import com.splan.xbet.back.backforbusiness.param.CheckCodeParam;
import com.splan.xbet.back.backforbusiness.param.GetCodeParam;
import com.splan.xbet.back.backforbusiness.param.RegisterParam;
import com.splan.xbet.back.backforbusiness.param.ResetPasswordParam;
import com.splan.xbet.back.backforbusiness.service.LoginService;
import com.splan.xbet.back.backforbusiness.service.PermissionService;
import com.splan.xbet.back.backforbusiness.utils.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JavaSmsApi javaSmsApi;

    @Autowired
    private FrontCompanyInfoMapper frontCompanyInfoMapper;

    @Autowired
    private MailService mailService;

    @Value("${mail.mailSubject}")
    public String mailSubject;

    @Value("${phone-msg.key}")
    public String msgKey;

    @Autowired
    private HttpServletRequest request;

    @Override
    public CommonResult<JSONObject> authLogin(JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        JSONObject info = new JSONObject();
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            currentUser.login(token);
            //若未关联则抛出异常
            JSONObject loginInfo=(JSONObject) SecurityUtils.getSubject().getSession().getAttribute(Constants.SESSION_USER_INFO);
            if(0==loginInfo.getIntValue("roleid"))
                return ResultUtil.returnError(ResultStatus.NO_JURISDICTION);
            SysUser sysUser=new SysUser();
            sysUser.setLoginIp(CommonUtil.getIpAddr(httpServletRequest));
            sysUserMapper.update(sysUser,new UpdateWrapper<SysUser>().eq("username",username));
            return ResultUtil.returnSuccess(info);
        } catch (AuthenticationException e) {
            return ResultUtil.returnError(ResultStatus.LOGIN_FAIL);
        }
    }

    /**
     * 根据用户名和密码查询对应的用户
     */
    @Override
    public JSONObject getUser(String username, String password) {
        return sysUserMapper.getUser(username, password);
    }

    /**
     * 查询当前登录用户的权限等信息
     */
    @Override
    public JSONObject getInfo() {
        //从session获取用户信息
        Session session = SecurityUtils.getSubject().getSession();
        JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_INFO);
        String username = userInfo.getString("username");
        String roleid = userInfo.getString("roleid");
        JSONObject info = new JSONObject();
        JSONObject userPermission = permissionService.getUserPermission(username);
        String roleName="";
        if(null!=sysRoleMapper.selectById(roleid))
            roleName=sysRoleMapper.selectById(roleid).getRoleName();
        session.setAttribute(Constants.SESSION_USER_PERMISSION, userPermission);
        session.setAttribute(Constants.SESSION_USER_ROLE, roleName);
        info.put("userPermission", userPermission);
        return info;
    }

    /**
     * 退出登录
     */
    @Override
    public String logout() {
        try {
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.logout();
        } catch (Exception e) {
        }
        return "";
    }

    @Override
    public CommonResult register(RegisterParam registerParam) {
        String apiId=request.getHeader("api_id");
        if(!registerParam.getPassword().equals(registerParam.getConfirmPassword()))
            return ResultUtil.returnError(ResultStatus.OPERATIONFAIL);
        String tempApi="";
        QueryWrapper<SysUser> queryWrapper=new QueryWrapper<>();
        if(StringUtils.isNotBlank(registerParam.getPhone())) {
            queryWrapper.eq("username", registerParam.getPhone());
            tempApi=registerParam.getPhone();
        }

        if(StringUtils.isNotBlank(registerParam.getEmail())) {
            queryWrapper.eq("username", registerParam.getEmail());
            tempApi=registerParam.getEmail();
        }

        if(sysUserMapper.selectOne(queryWrapper)!=null)
            return ResultUtil.returnError(ResultStatus.SYS_EXISTS);

        SysUser sysUser=new SysUser();

        if(StringUtils.isNotBlank(apiId))
            sysUser.setApiid(apiId);
        else
            sysUser.setApiid(tempApi);

        sysUser.setRoleId(1);

        if(StringUtils.isNotBlank(registerParam.getPhone())){

            if(redisTemplate.opsForValue().get(RedisContant.MSG+registerParam.getPhone())==null)
                return ResultUtil.returnError(ResultStatus.OPERATIONFAIL);
            if(!registerParam.getCode().equals(redisTemplate.opsForValue().get(RedisContant.MSG+registerParam.getPhone())))
                return ResultUtil.returnError(ResultStatus.CODE_ERROR);

            sysUser.setUsername(registerParam.getPhone());
            sysUser.setPassword(registerParam.getPassword());
            sysUser.setRegisterType(RegisterType.PHONE);
            sysUser.setNickname(registerParam.getPhone());
            sysUser.setEmail(registerParam.getBindingEmail());
            int x=sysUserMapper.insert(sysUser);

            FrontCompanyInfo frontCompanyInfo=new FrontCompanyInfo();
            frontCompanyInfo.setName(registerParam.getCompanyName());
            frontCompanyInfo.setSysId(sysUser.getId());
            frontCompanyInfo.setStatus(true);
            x=frontCompanyInfoMapper.insert(frontCompanyInfo);

            return ResultUtil.returnSuccess(x);

        }else if(StringUtils.isNotBlank(registerParam.getEmail())){
            if(redisTemplate.opsForValue().get(RedisContant.EMAIL+registerParam.getEmail())==null)
                return ResultUtil.returnError(ResultStatus.OPERATIONFAIL);
            if(!registerParam.getCode().equals(redisTemplate.opsForValue().get(RedisContant.EMAIL+registerParam.getEmail())))
                return ResultUtil.returnError(ResultStatus.CODE_ERROR);

            sysUser.setUsername(registerParam.getEmail());
            sysUser.setPassword(registerParam.getPassword());
            sysUser.setRegisterType(RegisterType.EMAIL);
            sysUser.setNickname(registerParam.getEmail());
            sysUser.setEmail(registerParam.getBindingEmail());
            int x=sysUserMapper.insert(sysUser);

            FrontCompanyInfo frontCompanyInfo=new FrontCompanyInfo();
            frontCompanyInfo.setName(registerParam.getCompanyName());
            frontCompanyInfo.setSysId(sysUser.getId());
            frontCompanyInfo.setStatus(true);
            x=frontCompanyInfoMapper.insert(frontCompanyInfo);

            return ResultUtil.returnSuccess(x);

        }else{
            return ResultUtil.returnError(ResultStatus.OPERATIONFAIL);
        }
    }

    @Override
    public CommonResult getCode(GetCodeParam getCodeParam) {
        if(StringUtils.isNotBlank(getCodeParam.getPhone())) {
            if (redisTemplate.opsForValue().get(RedisContant.MSG + getCodeParam.getPhone()) != null)
                return ResultUtil.returnError(ResultStatus.TYY_AGAIN);

            String code = CommonUtil.randomCode();
            String text = MsgTestContant.inText;
            text = String.format(text, code);
            try {
                JavaSmsApi.sendSms(msgKey, text, getCodeParam.getPhone());
                redisTemplate.opsForValue().set(RedisContant.MSG + getCodeParam.getPhone(), code, 10 , TimeUnit.MINUTES);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ResultUtil.returnSuccess(1);
        }else if(StringUtils.isNotBlank(getCodeParam.getEmail())){
            if (redisTemplate.opsForValue().get(RedisContant.EMAIL + getCodeParam.getEmail()) != null){
                return ResultUtil.returnError(ResultStatus.TYY_AGAIN);
            }
            String code = CommonUtil.randomCode();
            String text = MsgTestContant.inText;
            text = String.format(text, code);
            mailService.sendSimpleMail(getCodeParam.getEmail(),mailSubject,text);
            redisTemplate.opsForValue().set(RedisContant.EMAIL + getCodeParam.getEmail(), code, 10 ,TimeUnit.MINUTES);
            return ResultUtil.returnSuccess(1);
        }
        return ResultUtil.returnSuccess(1);
    }

    @Override
    public CommonResult checkCode(CheckCodeParam checkCodeParam) {

        if(StringUtils.isNotBlank(checkCodeParam.getPhone())) {
            if (redisTemplate.opsForValue().get(RedisContant.MSG + checkCodeParam.getPhone()) == null)
                return ResultUtil.returnError(ResultStatus.CODE_ERROR);
            if(!checkCodeParam.getCode().equals(redisTemplate.opsForValue().get(RedisContant.MSG + checkCodeParam.getPhone())))
                return ResultUtil.returnError(ResultStatus.CODE_ERROR);
        }else if(StringUtils.isNotBlank(checkCodeParam.getEmail())){
            if (redisTemplate.opsForValue().get(RedisContant.EMAIL + checkCodeParam.getEmail()) == null)
                return ResultUtil.returnError(ResultStatus.CODE_ERROR);
            if(!checkCodeParam.getCode().equals(redisTemplate.opsForValue().get(RedisContant.EMAIL + checkCodeParam.getEmail())))
                return ResultUtil.returnError(ResultStatus.CODE_ERROR);
        }
        return ResultUtil.returnSuccess(1);

    }

    @Override
    public CommonResult resetPassword(ResetPasswordParam resetPasswordParam) {
        if(!resetPasswordParam.getPassword().equals(resetPasswordParam.getConfirmPassword()))
            return ResultUtil.returnError(ResultStatus.PASSWORD_INCONSISTENT);

        UpdateWrapper<SysUser> userUpdateWrapper=new UpdateWrapper<>();
        SysUser sysUser=new SysUser();
        if(StringUtils.isNotBlank(resetPasswordParam.getPhone())) {
            userUpdateWrapper.eq("username",resetPasswordParam.getPhone());
        }else if(StringUtils.isNotBlank(resetPasswordParam.getEmail())){
            userUpdateWrapper.eq("username",resetPasswordParam.getEmail());
        }
        sysUser.setPassword(resetPasswordParam.getPassword());
        sysUserMapper.update(sysUser,userUpdateWrapper);
        return ResultUtil.returnSuccess(1);
    }

    public static void main(String[] args) {
        String code=CommonUtil.randomCode();
        String text= MsgTestContant.inText;
        text= String.format(text, code);
        System.out.println(text);
    }

}
