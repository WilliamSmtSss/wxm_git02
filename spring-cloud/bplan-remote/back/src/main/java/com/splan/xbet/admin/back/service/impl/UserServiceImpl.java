package com.splan.xbet.admin.back.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.splan.base.bean.ProxyConfig;
import com.splan.base.bean.SysUser;
import com.splan.base.bean.UserBean;
import com.splan.xbet.admin.back.mappers.ProxyConfigMapper;
import com.splan.xbet.admin.back.mappers.UserMapper;
import com.splan.xbet.admin.back.service.ISysUserService;
import com.splan.xbet.admin.back.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserBean> implements IUserService {
//
//    @Autowired
//    private RedisTemplate<String,String> redisTemplate;
//
//    @Autowired
//    private UserMapper userMapper;
//
//    @Autowired
//    private UserBalanceBeanMapper userBalanceBeanMapper;
//
//    @Autowired
//    private TokenManager tokenManager;
//
//    @Autowired
//    private IBetOrderService betOrderService;
//
//    @Autowired
//    private MemberInterestsBeanMapper memberInterestsBeanMapper;
//
//    @Autowired
//    private IUserGiftTaskService userGiftTaskService;
//
//    @Autowired
//    private IGiftListService giftListService;
//
//    @Autowired
//    private INoticeService noticeService;
//
    @Autowired
    private ProxyConfigMapper proxyConfigMapper;
//
    @Autowired
    private ISysUserService SysUserService;
//
//    @Autowired
//    private BetOrderBeanMapper betOrderBeanMapper;
//
//    @Autowired
//    private IUserCardService iUserCardService;
//
//    @Autowired
//    private IasService iasService;
//
//    private ExecutorService service = Executors.newFixedThreadPool(9);
//
//    @Autowired
//    private IUserLoginLogService userLoginLogService;
//
//    @Autowired
//    private IUserBalanceService userBalanceService;
//
//    @Autowired
//    private IAsOrderService asOrderService;
//
//    @Autowired
//    private PayOrderBeanMapper payOrderBeanMapper;
//
//    private final String WALLET = "/user/wallet";
//
//    private final String PRE = "invite_activity_";
//
//    @Autowired
//    private IMoneyRecordService moneyRecordService;
//
//    @Autowired
//    private IFriendRecordService friendRecordService;
//
//    @Autowired
//    private IPayOrderService payOrderService;
//
//    @Value("${spring.profiles.active}")
//    private String activeValue;
//
//
//    @Override
//    @Transactional
//    public CommonResult<UserResult> register(UserDto user) {
//        /**
//         * 1.校验验证码
//         * 2.判断用户是否存在
//         * 3.注册用户
//         * 3.1处理邀请人
//         * 4.生成资产表数据
//         * 5.返回token
//         */
//        String redisCode = redisTemplate.opsForValue().get(MessageType.REGISTER.getName() +user.getMobileArea()+user.getMobile());
//        if (StringUtils.isEmpty(redisCode) || !user.getVerificationCode().equals(redisCode)){
//            return ResultUtil.returnError(ResultStatus.VERIFICATIONCODEERROR);
//        }
//        UserBean userBean = userMapper.findByMobile(user.getMobileArea(),user.getMobile());
//        if (null != userBean){
//            return ResultUtil.returnError(ResultStatus.USER_IS_EXISTS);
//        }
//        userBean = new UserBean();
//        BeanUtils.copyProperties(user,userBean);
//        /**
//         * salt生成
//         */
//        String salt = PasswordEncoder.generateSalt();
//        userBean.setSalt(salt);
//        PasswordEncoder encoderMd5 = new PasswordEncoder(salt, "SHA");
//        String encodePwd = encoderMd5.encode(user.getPwd());
//        userBean.setPassword(encodePwd);
//        userBean.setLastLoginTime(new Date());
//        userBean.setStatus(Status.ENABLE);
//        userBean.setLevel(Level.Normal);
//        if (StringUtils.isBlank(user.getUserName())){
//            userBean.setUsername(NameUtil.getStringRandom("ST",10));
//        }else {
//            userBean.setUsername(user.getUserName());
//        }
//
//        /**
//         * 会员相关
//         */
//        userBean.setMemberLevel(1);
//        userBean.setMemberStatus(Status.ENABLE);
//        userBean.setBirthdayCheck(0);
//        if (StringUtils.isBlank(user.getRegisterChannel())){
//            userBean.setRegisterChannel(RegisterChannel.H5);
//        }
//        //userBean.setInviteCode(NameUtil.toSerialCode());
//        userMapper.insert(userBean);
//        //userBean. 根据id生成邀请码
//        String inviteCode = NameUtil.toSerialCode(userBean.getId());
//        userBean.setInviteCode(inviteCode);
//        userMapper.updateById(userBean);
//
//
//        UserBalanceBean userBalanceBean = saveUserBalanceBean(userBean);
//
//        // 生成一个 token，保存用户登录状态
//        TokenModel model = tokenManager.createToken (userBean.getId ());
//        UserResult userResult = new UserResult();
//        userResult.setUserId(model.getUserId());
//        userResult.setToken(model.getToken());
//
//        userResult.setHasActive(0);
//        userResult.setHasMessage(0);
//        userResult.setCoin(userBalanceBean.getCoin());
//
//
//        return ResultUtil.returnSuccess(userResult);
//    }
//
//    @Override
//    public CommonResult<TokenModel> loginregister(UserCodeDto userDto) {
//        String redisCode = redisTemplate.opsForValue().get(MessageType.REGISTER.getName() +userDto.getMobileArea()+userDto.getUsername());
//        if (StringUtils.isEmpty(redisCode) || !userDto.getVerificationCode().equals(redisCode)){
//            return ResultUtil.returnError(ResultStatus.VERIFICATIONCODEERROR);
//        }
//        UserBean userBean = userMapper.findByMobile(userDto.getMobileArea(),userDto.getUsername());
//        if (null != userBean){
//            return login(userBean,userDto);
//            //登录操作
//        }else {
//            /*if (StringUtils.isNotBlank(userDto.getSimplecaptcha())){
//                String code = redisTemplate.opsForValue().get(userDto.getSimplecaptcha());
//                if (StringUtils.isNotBlank(code)){
//                    redisTemplate.delete(userDto.getSimplecaptcha());
//                    if (!code.equals(userDto.getCaptchaCode())){
//                        return ResultUtil.returnError(ResultStatus.VERIFICATIONCODEERROR);
//                    }
//                }else {
//                    return ResultUtil.returnError(ResultStatus.VERIFICATIONCODEERROR);
//                }
//
//            }else {
//                return ResultUtil.returnError(ResultStatus.INPUTVERIFICATIONCODE);
//            }*/
//        }
//        Integer ipCount = userMapper.countByIp(userDto.getRegisterIp());
//        if (ipCount>5 && activeValue.contains("storm")){
//            return ResultUtil.returnError(ResultStatus.IP_TOO_BUSY);
//        }
//        userBean = new UserBean();
//        BeanUtils.copyProperties(userDto,userBean);
//        /**
//         * salt生成
//         */
//        userBean.setMobile(userDto.getUsername());
//        String salt = PasswordEncoder.generateSalt();
//        userBean.setSalt(salt);
//        userBean.setLastLoginTime(new Date());
//        userBean.setStatus(Status.ENABLE);
//        userBean.setLevel(Level.Normal);
//
//        userBean.setUsername(NameUtil.getStringRandom("ST",10));
//        userBean.setNickname(userBean.getUsername());
//
//        /**
//         * 会员相关
//         */
//        userBean.setMemberLevel(1);
//        userBean.setMemberStatus(Status.ENABLE);
//        userBean.setBirthdayCheck(0);
//        if (StringUtils.isBlank(userDto.getRegisterChannel())){
//            userBean.setRegisterChannel(RegisterChannel.H5);
//        }else {
//            userBean.setRegisterChannel(RegisterChannel.valueOf(userDto.getRegisterChannel()));
//        }
//        userBean.setChannel(userDto.getChannel());
//        userBean.setDomain(userDto.getDomain());
//        userMapper.insert(userBean);
//        //userBean. 根据id生成邀请码
//        String inviteCode = NameUtil.toSerialCode(userBean.getId());
//        userBean.setInviteCode(inviteCode);
//        userMapper.updateById(userBean);
//        //邀请任务
//        //friendRecordService.createFriendRecord(userBean,FriendEnums.REGISTER);
//
//
//        UserBalanceBean userBalanceBean = saveUserBalanceBean(userBean);
//
//        // 生成一个 token，保存用户登录状态
//        TokenModel model = tokenManager.createToken (userBean.getId ());
//        UserResult userResult = new UserResult();
//        userResult.setUserId(model.getUserId());
//        userResult.setToken(model.getToken());
//
//        userResult.setHasActive(0);
//        userResult.setHasMessage(0);
//        userResult.setCoin(userBalanceBean.getCoin());
//
//        // 注册充值任务完成
////        userGiftTaskService.register(userBean);
//
//        return ResultUtil.returnSuccess(model,ResultStatus.REGISTER_SUCCESS);
//    }
//
//    /**
//     * 登录
//     * @param userBean
//     * @param userDto
//     * @return
//     */
//    private CommonResult<TokenModel> login(UserBean userBean,UserCodeDto userDto){
//        //登录
//        if (userBean.getStatus().equals(Status.DISABLE)){
//            //账户禁用
//            return ResultUtil.returnError(ResultStatus.USER_DISABLE);
//        }
//
//        if (userBean.getStatus().equals(Status.LOCK)){
//            long startDate = userBean.getLastLoginTime().getTime();
//            long nowDate = (new Date()).getTime();
//            if ((nowDate-startDate)>1*60*60*1000){
//                userBean.setLoginCount(0);
//                userBean.setStatus(Status.ENABLE);
//                //解锁
//            }else {
//                //账户禁用
//                return ResultUtil.returnError(ResultStatus.USER_LOCK);
//            }
//
//        }
//
//        /*if (StringUtils.isNotBlank(userDto.getSimplecaptcha())){
//            String code = redisTemplate.opsForValue().get(userDto.getSimplecaptcha());
//            if (StringUtils.isNotBlank(code)){
//                redisTemplate.delete(userDto.getSimplecaptcha());
//                if (!code.equals(userDto.getCaptchaCode())){
//                    return ResultUtil.returnError(ResultStatus.VERIFICATIONCODEERROR);
//                }
//            }else {
//                return ResultUtil.returnError(ResultStatus.VERIFICATIONCODEERROR);
//            }
//
//        }else {
//            UserBalanceBean userBalanceBean = userBalanceBeanMapper.selectUserBalanceByUserId(userBean.getId());
//            if (userBalanceBean.getDepositCoin().compareTo(BigDecimal.ZERO)<1){
//                return ResultUtil.returnError(ResultStatus.INPUTVERIFICATIONCODE);
//            }
//        }*/
//
//        userBean.setLoginCount(0);
//        userBean.setLastLoginTime(new Date());
//        userMapper.updateById(userBean);
//
//
//        // 生成一个 token，保存用户登录状态
//        TokenModel model = tokenManager.createToken (userBean.getId ());
//        if (userBean.getSyncFlag()!=null && userBean.getSyncFlag()==2){
//            OutTransferThread outTransferThread = new OutTransferThread(iasService,userBean);
//            service.submit(outTransferThread);
//        }
//
//        userLoginLogService.createLoginLog(userBean.getId(), LoginType.MOBILE, userDto.getRegisterIp(), userDto.getTerminal());
//        return ResultUtil.returnSuccess(model);
//    }
//
//    @Override
//    public CommonResult<UserResult> getUserInfo(UserBean user) {
//        /**
//         * 获取金额
//         * 获取消息
//         */
//        UserBalanceBean userBalanceBean = userBalanceBeanMapper.selectUserBalanceByUserId(user.getId());
//        UserResult userResult = new UserResult();
//        userResult.setRealName(user.getRealName());
//        userResult.setUsername(user.getUsername());
//        userResult.setNickname(user.getNickname());
//        userResult.setCoin(userBalanceBean.getAvailableCoin());
//        userResult.setQq(user.getQq());
//        userResult.setVirtualId(user.getInviteCode());
//        QueryWrapper<NoticeBean> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("action",NoticeAction.message);
//        queryWrapper.eq("is_read",0);
//        queryWrapper.eq("status",Status.ENABLE);
//        queryWrapper.eq("user_id",user.getId());
//        Integer messageCount = noticeService.count(queryWrapper);
//        userResult.setHasMessage(messageCount);
//        userResult.setHasActive(0);
//        userResult.setLevel(user.getLevel());
//        userResult.setUserId(user.getId());
//        if (StringUtils.isBlank(user.getPassword())){
//            userResult.setHasPassword(1);
//        }
//        QueryWrapper<UserCardBean> queryWrapper1 = new QueryWrapper<>();
//        queryWrapper1.eq("user_id",user.getId());
//        Integer cardCount = iUserCardService.count(queryWrapper1);
//        if (cardCount==0){
//            userResult.setHasCard(1);
//        }
//        if (userBalanceBean.getLimitCoin().compareTo(BigDecimal.ZERO)<1){
//            userResult.setAvailableWithdrawCoin(userBalanceBean.getAvailableCoin().setScale(0,BigDecimal.ROUND_DOWN));
//        }else {
//            userResult.setAvailableWithdrawCoin(BigDecimal.ZERO);
//        }
//        userResult.setLimitCoin(userBalanceBean.getLimitCoin());
//
//
//        //waibu
//        userResult = getOutCoin(user,userBalanceBean,userResult);
//        return ResultUtil.returnSuccess(userResult);
//    }
//
//    private UserResult getOutCoin(UserBean user,UserBalanceBean userBalanceBean,UserResult userResult){
//        if (StringUtils.isNotBlank(user.getApiId())){
//            WalletDto walletDto = new WalletDto(user.getExtraId(),user.getApiId());
//            ProxyConfig proxyConfig = proxyConfigMapper.selectProxyConfigByClientId(user.getApiId());
//            if (null!=proxyConfig){
//                if (proxyConfig.getEncode()==0){
//                    String sign = SignUtil.getSign(walletDto,proxyConfig.getClientSecret());
//                    walletDto.setSign(sign);
//                }
//                CommonResult<JSONObject> commonResult = OKHttpUtil.httpPostOperate(proxyConfig.getUrl()+WALLET, JSON.toJSONString(walletDto),false);
//                if (commonResult.isSuccess()) {
//                    userResult.setCoin(commonResult.getData().getBigDecimal("coin"));
//                    if (userResult.getCoin().compareTo(userBalanceBean.getAvailableCoin())!=0){
//                        userBalanceBean.setCoin(userResult.getCoin());
//                        userBalanceBean.setAvailableCoin(userResult.getCoin());
//                        userBalanceBeanMapper.updateById(userBalanceBean);
//                    }
//                }
//            }
//        }
//        return userResult;
//
//    }
//
//    @Override
//    public CommonResult<String> modifyMobile(UserBean user) {
//        String redisCode = redisTemplate.opsForValue().get(MessageType.REGISTER.getName() +user.getMobileArea()+user.getMobile());
//        if (org.springframework.util.StringUtils.isEmpty(redisCode)){
//            return ResultUtil.returnError(ResultStatus.VERIFICATIONCODEERROR);
//        }
//        userMapper.updateById(user);
//        return ResultUtil.returnSuccess("");
//    }
//
//    @Override
//    public CommonResult<String> modifyPassword(UserBean user, PasswordDto password) {
//        if (!StringUtils.equals(password.getNewPassword(), password.getSurePassword())) {
//            return ResultUtil.returnError(ResultStatus.PASSWORD_NOT_EQUAL);
//        }
//        PasswordEncoder encoderMd5 = new PasswordEncoder(user.getSalt(), "SHA");
//        if (!StringUtils.equals(encoderMd5.encode(password.getBeforePassword()), user.getPassword())) {
//            return ResultUtil.returnError(ResultStatus.PASSWORD_NOT_BEFORE);
//        }
//        String encodePwd = encoderMd5.encode(password.getNewPassword());
//        user.setPassword(encodePwd);
//        userMapper.updateById(user);
//        return ResultUtil.returnSuccess("");
//    }
//
//    @Override
//    public CommonResult<String> modifyPassword(PasswordDto password) {
//        String redisCode = redisTemplate.opsForValue().get(MessageType.FORGET.getName() +password.getMobileArea()+password.getMobile());
//        if (StringUtils.isEmpty(redisCode) || !password.getVerificationCode().equals(redisCode)){
//            return ResultUtil.returnError(ResultStatus.VERIFICATIONCODEERROR);
//        }
//        UserBean user =  userMapper.findByMobile(password.getMobileArea(),password.getMobile());
//        if (null == user) {
//            ResultUtil.returnError(ResultStatus.USER_NOT_FOUND);
//        }
//        PasswordEncoder encoderMd5 = new PasswordEncoder(user.getSalt(), "SHA");
//        String encodePwd = encoderMd5.encode(password.getNewPassword());
//        user.setPassword(encodePwd);
//        userMapper.updateById(user);
//        return ResultUtil.returnSuccess("");
//    }
//
//
//    /**
//     * 增加初始赠送
//     * @param userBean
//     * @return
//     */
//    private UserBalanceBean saveUserBalanceBean(UserBean userBean){
////        GiftListBean giftListBean = (GiftListBean) GiftTaskConstants.get("1");
////        BigDecimal registerCoin = new BigDecimal(0);
////        if (Status.ENABLE.equals(giftListBean.getStatus())) {
////            registerCoin.add(giftListBean.getGiftValue());
////        }
//
//        UserBalanceBean userBalanceBean = new UserBalanceBean();
//        userBalanceBean.setUserId(userBean.getId());
//        /*userBalanceBean.setAvailableCoin(SysParamConstants.getBigDecimal(SysParamConstants.BALANCE_GIFT_COIN));
//        userBalanceBean.setCoin(SysParamConstants.getBigDecimal(SysParamConstants.BALANCE_GIFT_COIN));
//        userBalanceBean.setLimitCoin(SysParamConstants.getBigDecimal(SysParamConstants.BALANCE_GIFT_COIN));*/
//        userBalanceBeanMapper.insert(userBalanceBean);
//        return userBalanceBean;
//    }
//
//    private UserBalanceBean saveAppUserBalanceBean(UserBean userBean,BigDecimal coin){
//
//        UserBalanceBean userBalanceBean = new UserBalanceBean();
//        userBalanceBean.setUserId(userBean.getId());
//        userBalanceBean.setAvailableCoin(coin);
//        userBalanceBean.setCoin(coin);
//        userBalanceBean.setLimitCoin(coin);
//        userBalanceBeanMapper.insert(userBalanceBean);
//        return userBalanceBean;
//    }
//
//    @Override
//    public JSONObject getRegisterInfo(JSONObject jsonObject) {
//        JSONObject jsonObjectResult = new JSONObject();
//
//        Integer total = count();
//        jsonObjectResult.put("total",total);//总注册
//
//        QueryWrapper<UserBean> queryWrapper = new QueryWrapper<>();
//        queryWrapper.groupBy("register_channel");
//        queryWrapper.select("count(*) as a ","register_channel");
//        List<Map<String,Object>> list = listMaps(queryWrapper);
//        list.forEach((m)-> jsonObjectResult.put(m.get("register_channel").toString(),m.get("a")));
//
//        queryWrapper = new QueryWrapper<>();
//        queryWrapper.apply("to_days(create_time) = to_days(now())")
//        //queryWrapper.eq("to_days(create_time)","to_days(now())")
//                .isNull("be_invite_code");
//        Integer normalCount = count(queryWrapper);
//        jsonObjectResult.put("normalCount",normalCount);//今日普通注册
//
//
//        queryWrapper = new QueryWrapper<>();
//        queryWrapper.apply("to_days(create_time) = to_days(now())")
//        //queryWrapper.eq("to_days(create_time)","to_days(now())")
//                .isNotNull("be_invite_code");
//        Integer inviteCount = count(queryWrapper);
//        jsonObjectResult.put("inviteCount",inviteCount);//今日邀请注册
//
//        jsonObjectResult.put("todayCount",normalCount+inviteCount);//今日总注册
//
//        return jsonObjectResult;
//    }
//
//    @Override
//    public List<Map<String, Object>> getDayRegisterInfo(JSONObject jsonObject) {
//        QueryWrapper<UserBean> queryWrapper = new QueryWrapper<>();
//        queryWrapper.select("DATE_FORMAT(create_time,'%Y-%m-%d') days ,count(*) as dayscount ")
//               // .gt("")
//                .groupBy("days")
//                .orderByDesc("days");
//        if (jsonObject.getString("startTime")!=null){
//            queryWrapper.ge("DATE_FORMAT(create_time,'%Y-%m-%d')",jsonObject.getString("startTime"));
//        }
//        if (jsonObject.getString("endTime")!=null){
//            queryWrapper.le("DATE_FORMAT(create_time,'%Y-%m-%d')",jsonObject.getString("endTime"));
//        }
//        return listMaps(queryWrapper);
//    }
//
//    @Override
//    public CommonResult<IPage<UserAccountResult>> selectAll(JSONObject jsonObject, Page page,Level... levels) {
//        List<Level> level = Arrays.asList(levels);
//        if (level.size() == 0) {
//            level = null;
//        }
//        List<UserAccountResult> userBeanList = userMapper.selectAll(page,jsonObject.getLong("userId"),jsonObject.getString("mobile"),level);
//        if(levels.length!=0){
//            userBeanList.forEach(userAccountResult -> {
//                QueryWrapper<UserBalanceBean> queryWrapper=new QueryWrapper<>();
//                queryWrapper.eq("user_id",userAccountResult.getId());
//                userAccountResult.setUserBalanceBean(userBalanceBeanMapper.selectOne(queryWrapper));
//
//                QueryWrapper<UserCardBean> queryWrapper1=new QueryWrapper<>();
//                queryWrapper.eq("user_id",userAccountResult.getId());
//                userAccountResult.setUserCardBean(iUserCardService.getOne(queryWrapper1));
//
//                int agentlen=0;
//                int normallen=0;
//                if(userAccountResult.getAgentids().size()!=0){
//                    agentlen=userAccountResult.getAgentids().size();
//                }else{
//                    userAccountResult.getAgentids().add(-1);
//                }
////                if(userAccountResult.getNormals().size()!=0){
////                    normallen=userAccountResult.getNormals().size();
////                }else{
////                    userAccountResult.getNormals().add(-1);
////                }
//                List<Integer> secondUsers=userMapper.getAllUserByfirstUsers(userAccountResult.getAgentids());
//                if(secondUsers.size()!=0){
//                    userAccountResult.setNormals(secondUsers);
//                    normallen=secondUsers.size();
//                }else{
//                    userAccountResult.getNormals().add(-1);
//                }
//                userAccountResult.setAgentidslen(agentlen);
//                userAccountResult.setNormalidslen(normallen);
//                int agentprofit=betOrderBeanMapper.getAllprofit(userAccountResult.getAgentids()).intValue();
//                int normalprofit=betOrderBeanMapper.getAllprofit(userAccountResult.getNormals()).intValue();
//                int totleprofit=agentprofit+normalprofit;
//                userAccountResult.setAgentprofit(agentprofit);
//                userAccountResult.setNormalprofit(normalprofit);
//                userAccountResult.setTotleprofit(totleprofit);
//            });
//        }
//        page.setRecords(userBeanList);
//
//        return ResultUtil.returnSuccess(page);
//    }
//
//    @Override
//    public CommonResult<IPage<UserAccountResult>> selectMoneyAll(JSONObject jsonObject, Page page) {
//        List<UserAccountResult> userBeanList = userMapper.selectMoneyAll(page,jsonObject.getLong("userId"),jsonObject.getString("mobile"));
//        page.setRecords(userBeanList);
//
//        return ResultUtil.returnSuccess(page);
//    }
//
//    @Override
//    public CommonResult<UserResult> getMemberInfo(UserBean user) {
//        MemberResult memberResult = userMapper.findMemberByUserId(user.getId());
//
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar ca = Calendar.getInstance();
//        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
//        String last = format.format(ca.getTime());
//        memberResult.setPeriod(last);//有效期
//
//        QueryWrapper<BetOrderBean> queryWrapper = new QueryWrapper<>();
//        queryWrapper.apply("date_format(create_time,'%Y-%m')","date_format(now(),'%Y-%m')");
//        queryWrapper.ge("odd",1.5);
//        queryWrapper.eq("status", OrderStatus.SETTLED);
//        queryWrapper.eq("tenant_user_no",user.getId());
//        queryWrapper.select("sum(amount) as a");
//        //queryWrapper.groupBy("tenant_user_no");
//        Map<String,Object> m = betOrderService.getMap(queryWrapper);
//        Integer sum = Integer.valueOf(m!=null?m.get("a").toString():"0");
//
//        QueryWrapper<AsOrderBean> asOrderBeanQueryWrapper = new QueryWrapper<>();
//        asOrderBeanQueryWrapper.apply("date_format(create_time,'%Y-%m')","date_format(now(),'%Y-%m')");
//        asOrderBeanQueryWrapper.eq("username",user.getUsername());
//        asOrderBeanQueryWrapper.select("sum(valid_account) as a");
//        Map<String,Object> m2 = asOrderService.getMap(asOrderBeanQueryWrapper);
//        Integer sum2 = new BigDecimal(m2!=null?m2.get("a").toString():"0").intValue();
//
//        memberResult.setMonthOrderCoin(sum+sum2);
//
//
//        /**
//         * 获取金额
//         * 获取消息
//         */
//        UserBalanceBean userBalanceBean = userBalanceBeanMapper.selectUserBalanceByUserId(user.getId());
//        UserResult userResult = new UserResult();
//        userResult.setRealName(user.getRealName());
//        userResult.setUsername(user.getUsername());
//        userResult.setCoin(userBalanceBean.getAvailableCoin());
//        userResult.setQq(user.getQq());
//        userResult.setVirtualId(user.getInviteCode());
//        QueryWrapper<NoticeBean> queryWrapper2 = new QueryWrapper<>();
//        queryWrapper2.eq("action",NoticeAction.message);
//        queryWrapper2.eq("is_read",0);
//        queryWrapper2.eq("status",Status.ENABLE);
//        queryWrapper2.eq("user_id",user.getId());
//        Integer messageCount = noticeService.count(queryWrapper2);
//        userResult.setHasMessage(messageCount);
//        userResult.setHasActive(0);
//        userResult.setLevel(user.getLevel());
//        userResult.setUserId(user.getId());
//        userResult.setMember(memberResult);
//        if (StringUtils.isBlank(user.getPassword())){
//            userResult.setHasPassword(1);
//        }
//        QueryWrapper<UserCardBean> queryWrapper1 = new QueryWrapper<>();
//        queryWrapper1.eq("user_id",user.getId());
//        Integer cardCount = iUserCardService.count(queryWrapper1);
//        if (cardCount==0){
//            userResult.setHasCard(1);
//        }
//        //userResult.setAvailableWithdrawCoin(userBalanceBean.getAvailableCoin().subtract(userBalanceBean.getLimitCoin()).setScale(0,BigDecimal.ROUND_DOWN));
//        if (userBalanceBean.getLimitCoin().compareTo(BigDecimal.ZERO)<1){
//            userResult.setAvailableWithdrawCoin(userBalanceBean.getAvailableCoin().setScale(0,BigDecimal.ROUND_DOWN));
//        }else {
//            userResult.setAvailableWithdrawCoin(BigDecimal.ZERO);
//        }
//        userResult.setLimitCoin(userBalanceBean.getLimitCoin());
//        return ResultUtil.returnSuccess(userResult);
//    }
//
//    @Override
//    public CommonResult<String> levelUp(UserBean user) {
//        MemberInterestsBean memberInterestsBean = memberInterestsBeanMapper.selectById(user.getMemberLevel());
//        UserBalanceBean userBalanceBean = userBalanceBeanMapper.selectUserBalanceByUserId(user.getId());
//        if (userBalanceBean.getOrderCoin().compareTo(new BigDecimal(memberInterestsBean.getUpOrderCoin()))>-1){
//            user.setMemberLevel(user.getMemberLevel()+1);
//            user.setMemberStatus(Status.ENABLE);
//            userMapper.updateById(user);
//            MemberInterestsBean memberInterestsBean2 = memberInterestsBeanMapper.selectById(user.getMemberLevel());
//            userBalanceService.changeCoin(user.getId(),memberInterestsBean2.getId().longValue(),new BigDecimal(memberInterestsBean2.getLevelUpGiftCoin()),MoneyAbleType.ACTIVITY);
//            //userBalanceBean.setGiftCoin(userBalanceBean.getGiftCoin().add(new BigDecimal(memberInterestsBean.getLevelUpGiftCoin())));
//            return ResultUtil.returnSuccess("");
//        }else {
//            return ResultUtil.returnError(ResultStatus.NOTTOUP);
//        }
//    }
//
    @Override
    public List<Long> getUserIds(String... apiid) {
        JSONObject sysUser = (JSONObject) SecurityUtils.getSubject().getPrincipal();
        List<String> apiids = new ArrayList<>();
        if(apiid.length!=0&& StringUtils.isNotBlank(apiid[0])) {
                List<String> apiidsin = Arrays.asList(apiid);
                List<String> apiidsreal=new ArrayList<>();
                for(String str:apiidsin){
                    List<ProxyConfig> proxyConfigs=proxyConfigMapper.getProxyConfigByVirtual(str);
                    apiidsreal.add(proxyConfigs.size()!=0?proxyConfigs.get(0).getClientId():"");
                }
                apiids.addAll(apiidsreal);
        }else{
            Integer apiId = sysUser.getInteger("userId");
            QueryWrapper<SysUser> queryWrapperSys = new QueryWrapper();
            queryWrapperSys.eq("id", apiId);
            List<SysUser> sysList = SysUserService.list(queryWrapperSys);
            SysUser sysuser = sysList.get(0);
            if (!"".equals(sysList.get(0).getJurisdiction().trim())) {
                String[] apiidss = sysuser.getJurisdiction().split("[,|，]");
                for (String str : apiidss) {
                    List<ProxyConfig> proxyConfigs=proxyConfigMapper.getProxyConfigByVirtual(str);
                    apiids.add(proxyConfigs.size()!=0?proxyConfigs.get(0).getClientId():"");
                }
            }
        }
//        queryWrapperSys=new QueryWrapper<>();
//        queryWrapperSys.eq("fromid",sysUser.getInteger("userId"));
//        List<SysUser> sysList2=SysUserService.list(queryWrapperSys);
//        if(sysList2.size()!=0){
//            sysList2.forEach(sysUser1 -> {
//                if(!apiids.contains(sysUser1.getJurisdiction()))apiids.add(sysUser1.getJurisdiction());
//            });
//        }

        if(apiids.size()==0)apiids.add("unmatching");
        QueryWrapper<UserBean> queryWrapper = new QueryWrapper();
        queryWrapper.select("id");
//        if (apiId != null) {
            //queryWrapper.eq("api_id", apiId);
            queryWrapper.in("api_id",apiids);
//        }
        List<Long> ids = new ArrayList<>();
        list(queryWrapper).forEach(userBean -> {
            ids.add(userBean.getId());
        });

        return ids;
    }
//
//    @Override
//    public CommonResult<UserResult> appUserlogin(AppUserDto appUserDto) {
//        /**
//         * 1.判断用户是否存在
//         * 2.生成资产表数据
//         * 3.返回token
//         */
//
//        UserBean userBean = userMapper.findByApiIdAndExtraId(appUserDto.getApiId(),appUserDto.getExtraId());
//        ProxyConfig proxyConfig = proxyConfigMapper.getProxyConfigByClientId(appUserDto.getApiId());
//        if (null != userBean){
//            String token = tokenManager.getTokenByUserId(userBean.getId());
//            TokenModel model = null;
//            if (StringUtils.isNotBlank(token)){
//                model = new TokenModel(userBean.getId(),token);
//            }else {
//                model = tokenManager.createToken (userBean.getId());
//            }
//            //TokenModel model = tokenManager.createToken (userBean.getId());
//            UserResult userResult = new UserResult();
//            userResult.setUserId(model.getUserId());
//            userResult.setToken(model.getToken());
//
//            userResult.setHasActive(0);
//            userResult.setHasMessage(0);
//            userResult.setCoin(appUserDto.getCoin());
//            if (StringUtils.isNotBlank(proxyConfig.getRedirectUri())){
//                userResult.setRedirectUri(proxyConfig.getRedirectUri()+"?token="+model.getUserId()+"_"+model.getToken());
//            }else {
//                userResult.setRedirectUri(SysParamConstants.getString(SysParamConstants.REDIRECT_URI)+"?token="+model.getUserId()+"_"+model.getToken());
//            }
//            //更新金额
//            UserBalanceBean userBalanceBean = userBalanceBeanMapper.selectUserBalanceByUserId(userBean.getId());
//            userBalanceBean.setCoin(appUserDto.getCoin());
//            userBalanceBean.setAvailableCoin(appUserDto.getCoin());
//            userBalanceBean.setFrozenCoin(BigDecimal.ZERO);
//            userBalanceBeanMapper.updateById(userBalanceBean);
//
//            userBean.setLastLoginTime(new Date());
//            userMapper.updateById(userBean);
//
//            return ResultUtil.returnSuccess(userResult);
//            //登陆
//            //return ResultUtil.returnError(ResultStatus.USER_IS_EXISTS);
//        }
//        userBean = new UserBean();
//        BeanUtils.copyProperties(appUserDto,userBean);
//        /**
//         * salt生成
//         */
//        String salt = PasswordEncoder.generateSalt();
//        userBean.setSalt(salt);
//        PasswordEncoder encoderMd5 = new PasswordEncoder(salt, "SHA");
//        String encodePwd = encoderMd5.encode(appUserDto.getExtraId());
//        userBean.setPassword(encodePwd);
//        userBean.setLastLoginTime(new Date());
//        userBean.setStatus(Status.ENABLE);
//        userBean.setLevel(Level.Normal);
//        userBean.setUsername(NameUtil.getStringRandom(appUserDto.getApiId(),10));
//        /**
//         * 会员相关
//         */
//        userBean.setMemberLevel(0);
//        userBean.setMemberStatus(Status.ENABLE);
//        userBean.setBirthdayCheck(0);
//        //if (StringUtils.isBlank(appUserDto.getRegisterChannel())){
//        userBean.setRegisterChannel(RegisterChannel.H5);
//        //}
//        //userBean.setInviteCode(NameUtil.toSerialCode());
//        userMapper.insert(userBean);
//        //userBean. 根据id生成邀请码
//        String inviteCode = NameUtil.toSerialCode(userBean.getId());
//        userBean.setInviteCode(inviteCode);
//        userMapper.updateById(userBean);
//
//
//        UserBalanceBean userBalanceBean = saveAppUserBalanceBean(userBean,appUserDto.getCoin());
//
//        // 生成一个 token，保存用户登录状态
//        TokenModel model = tokenManager.createToken (userBean.getId ());
//        UserResult userResult = new UserResult();
//        userResult.setUserId(model.getUserId());
//        userResult.setToken(model.getToken());
//
//        userResult.setHasActive(0);
//        userResult.setHasMessage(0);
//        userResult.setCoin(userBalanceBean.getCoin());
//        if (StringUtils.isNotBlank(proxyConfig.getRedirectUri())){
//            userResult.setRedirectUri(proxyConfig.getRedirectUri()+"?token="+model.getUserId()+"_"+model.getToken());
//        }else {
//            userResult.setRedirectUri(SysParamConstants.getString(SysParamConstants.REDIRECT_URI)+"?token="+model.getUserId()+"_"+model.getToken());
//        }
//
//        // 注册充值任务完成
////        userGiftTaskService.register(userBean);
//
//        return ResultUtil.returnSuccess(userResult);
//    }
//
//    public static void main(String[] args) {
//        PasswordEncoder encoderMd5 = new PasswordEncoder("Omt3lOlAkAI+DDP5vbm9", "SHA");
//        String encodePwd = encoderMd5.encode("a1234567");
//        System.out.println(encodePwd);
//    }
//
//    @Override
//    public CommonResult<Integer> updateUser(UserBean userBean, Level... levels) {
//        StringBuffer sb=new StringBuffer();
//        List<Level> levels1=Arrays.asList(levels);
//        if(levels1.size()==0){
//            levels1=null;
//        }
//        return ResultUtil.returnSuccess(userMapper.updateUser(userBean));
//    }
//
//    @Override
//    public CommonResult<IPage<UserBean>> listBlackUsers(Page page) {
//        page.setRecords(userMapper.listBlackUsers(page));
//        return ResultUtil.returnSuccess(page);
//    }
//
//    @Override
//    public CommonResult<Integer> relieveBlack(JSONObject jsonObject) {
//        return ResultUtil.returnSuccess(userMapper.relieveBlack(jsonObject.getInteger("userId")));
//    }
//
//    @Override
//    public List<Integer> getUserIdsByApiId(String apiId) {
//        if(StringUtils.isBlank(apiId))return null;
//        List<Integer> userIds=userMapper.getUserIdsByChannel(apiId);
//        if(userIds.size()==0)userIds.add(-1);
//        return userIds;
//    }
//
//    @Override
//    public CommonResult<String> setPassword(UserBean user, String password) {
//        if (StringUtils.isNotBlank(user.getPassword())){
//            return ResultUtil.returnError(ResultStatus.PASSWORD_IS_EXISTS);
//        }
//        PasswordEncoder encoderMd5 = new PasswordEncoder(user.getSalt(), "SHA");
//        String encodePwd = encoderMd5.encode(password);
//        user.setPassword(encodePwd);
//        userMapper.updateById(user);
//        return ResultUtil.returnSuccess("");
//    }
//
//    @Override
//    public CommonResult<UserBean> createUser(UserDto user) {
//        /**
//         * 1.校验验证码
//         * 2.判断用户是否存在
//         * 3.注册用户
//         * 3.1处理邀请人
//         * 4.生成资产表数据
//         * 5.返回token
//         */
//        String redisCode = redisTemplate.opsForValue().get(MessageType.REGISTER.getName() +user.getMobileArea()+user.getMobile());
//        if (StringUtils.isEmpty(redisCode) || !user.getVerificationCode().equals(redisCode)){
//            return ResultUtil.returnError(ResultStatus.VERIFICATIONCODEERROR);
//        }
//        UserBean userBean = userMapper.findByMobile(user.getMobileArea(),user.getMobile());
//        if (null != userBean){
//            return ResultUtil.returnError(ResultStatus.USER_IS_ALREADY_DOWN);
//        }
//        userBean = new UserBean();
//        BeanUtils.copyProperties(user,userBean);
//        /**
//         * salt生成
//         */
//        String salt = PasswordEncoder.generateSalt();
//        userBean.setSalt(salt);
//        PasswordEncoder encoderMd5 = new PasswordEncoder(salt, "SHA");
//        String encodePwd = encoderMd5.encode(user.getPwd());
//        userBean.setPassword(encodePwd);
//        userBean.setLastLoginTime(new Date());
//        userBean.setStatus(Status.ENABLE);
//        userBean.setLevel(Level.Normal);
//        if (StringUtils.isBlank(user.getUserName())){
//            userBean.setUsername(NameUtil.getStringRandom("ST",10));
//        }else {
//            userBean.setUsername(user.getUserName());
//        }
//
//        /**
//         * 会员相关
//         */
//        userBean.setMemberLevel(1);
//        userBean.setMemberStatus(Status.ENABLE);
//        userBean.setBirthdayCheck(0);
//        if (StringUtils.isBlank(user.getRegisterChannel())){
//            userBean.setRegisterChannel(RegisterChannel.H5);
//        }
//        //userBean.setInviteCode(NameUtil.toSerialCode());
//        userMapper.insert(userBean);
//        //userBean. 根据id生成邀请码
//        String inviteCode = NameUtil.toSerialCode(userBean.getId());
//        userBean.setInviteCode(inviteCode);
//        userMapper.updateById(userBean);
//
//        UserBalanceBean userBalanceBean = saveUserBalanceBean(userBean);
//
//        return ResultUtil.returnSuccess(userBean);
//    }
//
//    @Override
//    public CommonResult<InviteRewardResultOut> inviteRewardList(UserBean user,Page page) {
//        List<InviteRewardResult> inviteRewardResults=new ArrayList<>();
//        List<UserBean> userBeans=userMapper.getInviteList(page,user.getInviteCode());
//        InviteRewardResultOut result=new InviteRewardResultOut();
//        result.setPage(page);
//        List<UserBean> userBeansAll=userMapper.getInviteList(null,user.getInviteCode());
//        Integer totle=100*userBeansAll.size();
//        for(UserBean userBean:userBeans){
//            Integer waitRewardIn=0;
//            InviteRewardResult inviteRewardResult=new InviteRewardResult();
////            //注册
////            inviteRewardResult.setRegisterStatus(true);
////            waitRewardIn+=1;
////            //首投
////            if(betOrderBeanMapper.firstOrder(userBean.getId()+"")!=0){
////                inviteRewardResult.setFirstOrderStatus(true);
////                waitRewardIn+=2;
////            }
//            //首充
//            if(payOrderBeanMapper.firstRecharge(userBean.getId()+"")!=0){
//                inviteRewardResult.setFirstRechargeStatus(true);
//                waitRewardIn+=10;
//            }
////            BigDecimal totalOrder=betOrderBeanMapper.getBetOrderTotle(userBean.getId()+"").add(betOrderBeanMapper.getAsOrderTotle(userBean.getUsername()));
//            UserBalanceBean userBalanceBean=userBalanceBeanMapper.selectUserBalanceByUserId(userBean.getId());
//            BigDecimal totalOrder=userBalanceBean.getOrderCoin();
//            if(totalOrder.intValue()>=1000){
//                waitRewardIn+=5;
//            }
//            if(totalOrder.intValue()>=5000){
//                waitRewardIn+=25;
//            }
//            if(totalOrder.intValue()>=50000){
//                waitRewardIn+=60;
//            }
//            inviteRewardResult.setTotalOrder(totalOrder.intValue());
//            inviteRewardResult.setCreateTime(userBean.getCreateTime());
//            inviteRewardResult.setMobile(CommonUtil.hide(userBean.getMobile()));
//            inviteRewardResult.setWaitRewardIn(waitRewardIn);
//            inviteRewardResults.add(inviteRewardResult);
//        }
//        result.setTotalBenefits(totle);
//        QueryWrapper<UserBalanceBean> userBalanceBeanQueryWrapper=new QueryWrapper<>();
//        userBalanceBeanQueryWrapper.eq("user_id",user.getId());
//        UserBalanceBean userBalanceBean = userBalanceBeanMapper.selectOne(userBalanceBeanQueryWrapper);
//        result.setAlreadyBenefits(userBalanceBean.getAlreadyBenefits());
//        result.setWaitBenefits(userBalanceBean.getWaitBenefits());
//        result.setInviteRewardResults(inviteRewardResults);
//        return ResultUtil.returnSuccess(result);
//    }
//
//    @Override
//    public CommonResult<RewardInviteResult> getInviteReward(UserBean user) {
//        UserBalanceBean userBalanceBean = userBalanceBeanMapper.selectUserBalanceByUserId(user.getId());
//        if (userBalanceBean.getWaitBenefits()==0){
//            return ResultUtil.returnError(ResultStatus.NOT_ENOUGH_BENEFIT);
//        }
//        BigDecimal from = userBalanceBean.getAvailableCoin();
//        Integer amount = userBalanceBean.getWaitBenefits();
//        Integer x = userBalanceBeanMapper.updateBenefit(user.getId(),userBalanceBean.getVersion());
//        if (x>0){
//            RewardInviteResult rewardInviteResult = new RewardInviteResult();
//            rewardInviteResult.setReward(amount.toString());
//            payOrderService.createOffer(user,new BigDecimal(amount),MoneyAbleType.FRIEND_INVITE.getName());
//            moneyRecordService.createRecord(5l,userBalanceBean.getId(),MoneyAbleType.FRIEND_INVITE,from,new BigDecimal(amount),AlgorithmType.ADD,"邀请好友领取奖励",user.getId());
//            return ResultUtil.returnSuccess(rewardInviteResult);
//        }else {
//            return ResultUtil.returnError(ResultStatus.NOT_ENOUGH_BENEFIT);
//        }
//
//    }
//
//
}
