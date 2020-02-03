package com.splan.bplan.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.splan.base.enums.MessageType;
import com.splan.base.enums.ResultStatus;
import com.splan.bplan.http.CommonResult;
import com.splan.bplan.http.MessageChinaConfig;
import com.splan.bplan.http.MessageInternationalConfig;
import com.splan.bplan.http.sms.ChuangLanSmsUtil;
import com.splan.bplan.http.sms.SmsVariableRequest;
import com.splan.bplan.http.sms.SmsVariableResponse;
import com.splan.bplan.service.IMessageService;
import com.splan.bplan.utils.HttpUtil;
import com.splan.bplan.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 253.com 短信通道
 */
@Slf4j
@Service
public class Message253ServiceImpl implements IMessageService {

    @Autowired
    private MessageChinaConfig messageChinaConfig;

    @Autowired
    private MessageInternationalConfig messageInternationalConfig;

    private String BASE_PATH = "/send/json";

    private String CHINA_PATH = "/variable/json";

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    private final int LIMIT = 10;//每天短信调用次数

    private final int EXPIRESECOND = 3;//验证码有效期3分钟


    /**
     *
     * @param mobile 格式8618600000000
     * @param messageType
     */
    @Override
    public CommonResult<String> sendMessage(String mobileArea,String mobile, MessageType messageType) {
        String mobiles = mobileArea+mobile;
        String key = messageType.getName()+mobiles;

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String createDate = sdf.format(date);
        Long x = incr(createDate+key,24);
        if (x>LIMIT){
            //当天调用次数
            return ResultUtil.returnError(ResultStatus.LIMIT);
        }
        /**
         * 增加发送限制
         */
        Long expire = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        if (expire.longValue()>0 && expire.longValue()>120L){
            /**
             * 获取过于频繁
             */
            return ResultUtil.returnError(ResultStatus.TOO_MANY_RUN);
        }

        if (mobileArea.equals("86")){
            return chinaMessage(mobileArea,mobile,messageType);
        }else {
            return internationalMessage(mobileArea,mobile,messageType);
        }


    }

    /**
     * 国内短信通道
     * @param mobileArea
     * @param mobile
     * @param messageType
     * @return
     */
    private CommonResult<String> chinaMessage(String mobileArea,String mobile, MessageType messageType){

        String mobiles = mobileArea+mobile;
        String key = messageType.getName()+mobiles;
        if (!messageChinaConfig.isOpen()){
            String mecode = "666666";
            redisTemplate.opsForValue().set(key,mecode,EXPIRESECOND,TimeUnit.MINUTES);
            return ResultUtil.returnSuccess("");
        }

        //设置您要发送的内容：其中“【】”中括号为运营商签名符号，多签名内容前置添加提交
        String msg = "【X竞技】您的验证码是：{$var}";
        //参数组
        String paramsMessage = "%s,%s;";
        String mecode = randomCode();
        String params = String.format(paramsMessage,mobile,mecode);
        //状态报告
        String report= "true";

        SmsVariableRequest smsVariableRequest=new SmsVariableRequest(messageChinaConfig.getAccount(), messageChinaConfig.getPassword(), msg, params, report);

        String requestJson = JSON.toJSONString(smsVariableRequest);

        log.info("before request string is: " + requestJson);

        String response = ChuangLanSmsUtil.sendSmsByPost(messageChinaConfig.getUrl()+CHINA_PATH, requestJson);

        log.info("response after request result is : " + response);

        SmsVariableResponse smsVariableResponse = JSON.parseObject(response, SmsVariableResponse.class);

        if (smsVariableResponse.getCode().equals("0")){
            redisTemplate.opsForValue().set(key,mecode,EXPIRESECOND,TimeUnit.MINUTES);
        }else {
            return ResultUtil.returnError(ResultStatus.UNKNOWN_ERROR);
        }
        log.info("response  toString is : " + smsVariableResponse);
        return ResultUtil.returnSuccess("");
    }

    /**
     * 国际短信通道
     * @param mobileArea
     * @param mobile
     * @param messageType
     * @return
     */
    private CommonResult<String> internationalMessage(String mobileArea,String mobile, MessageType messageType){

        String key = messageType.getName()+mobileArea+mobile;
        String msg ="【storm】Your verification code is %s";

        String mecode = randomCode();
        String message = String.format(msg,mecode);

        //组装请求参数
        JSONObject map=new JSONObject();
        map.put("account", messageInternationalConfig.getAccount());
        map.put("password", messageInternationalConfig.getPassword());
        map.put("msg", message);
        map.put("mobile", mobileArea+mobile);
        map.put("senderId", "");

        String params=map.toString();

        log.info("请求参数为:" + params);
        try {
            String result= HttpUtil.post(messageInternationalConfig.getUrl()+BASE_PATH, params);

            log.info("返回参数为:" + result);

            JSONObject jsonObject =  JSON.parseObject(result);
            String code = jsonObject.get("code").toString();
            String msgid = jsonObject.get("msgid").toString();
            String error = jsonObject.get("error").toString();
            if (code.equals("0")){
                redisTemplate.opsForValue().set(key,mecode,EXPIRESECOND,TimeUnit.MINUTES);
            }else if(code.equals("110")){
                log.error("余额不足");
                //通知管理员
            }


            log.info("状态码:" + code + ",状态码说明:" + error + ",消息id:" + msgid);
        } catch (Exception e) {
            // TODO: handle exception
            log.error("请求异常：" + e);
        }
        return ResultUtil.returnSuccess("");
    }

    public Long incr(String key, long liveTime) {
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        Long increment = entityIdCounter.getAndIncrement();

        if ((null == increment || increment.longValue() == 0) && liveTime > 0) {//初始设置过期时间
            entityIdCounter.expire(liveTime, TimeUnit.HOURS);
        }
        return increment;
    }


    /**
     * //生成6位随机数
     */
    public static String randomCode() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }

    public static void main(String[] args) {
        String msg ="【storm】您的验证码是：[%s]";

        String mecode = randomCode();
        for (int i = 0; i < 1000; i++) {
            System.out.println(randomCode());
        }
        String message = String.format(msg,mecode);
        System.out.println(message);
    }

}
