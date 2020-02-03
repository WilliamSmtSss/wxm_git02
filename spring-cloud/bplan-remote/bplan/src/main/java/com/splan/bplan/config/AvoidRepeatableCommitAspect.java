package com.splan.bplan.config;

import com.alibaba.fastjson.JSONObject;
import com.splan.bplan.annotation.AvoidRepeatableCommit;
import com.splan.base.bean.UserBean;
import com.splan.base.enums.ResultStatus;
import com.splan.bplan.http.CommonResult;
import com.splan.bplan.service.RedisUtils;
import com.splan.bplan.utils.IpUtil;
import com.splan.bplan.utils.ResultUtil;
import org.apache.catalina.connector.RequestFacade;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;


@Aspect
@Order
@Component
@EnableAspectJAutoProxy(exposeProxy = true)
public class AvoidRepeatableCommitAspect {

    @Autowired
    HttpServletRequest request; // 这里可以获取到request

    @Autowired
    RedisUtils redisUtils;

    private static final String SPLIT = "#";

    /**
     * @param point
     */
    @SuppressWarnings("unchecked")
    @Around("@annotation(com.splan.bplan.annotation.AvoidRepeatableCommit)")
    public CommonResult around(ProceedingJoinPoint point) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        MethodSignature methodSignature = (org.aspectj.lang.reflect.MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();

        String accesstoken = request.getHeader("authorization");
        String userAgent = request.getHeader("User-Agent");
        String ip = IpUtil.getIpAddr(request);
        String uri = request.getRequestURI();
        String methodName = request.getMethod();
        String className = method.getDeclaringClass().getName();
        String name = method.getName();

        StringBuilder sb = new StringBuilder();
        sb.append(className)
                .append(SPLIT).append(uri)
                .append(SPLIT).append(ip)
                .append(SPLIT).append(methodName)
                .append(SPLIT).append(name)
                .append(SPLIT).append(accesstoken)
                .append(SPLIT).append(userAgent);

        // 请求参数
        if (point.getArgs() != null) {
            for (Object obj : point.getArgs()) {
                //System.out.println(obj.getClass());
                if (obj instanceof ShiroHttpServletRequest || obj instanceof UserBean) {
                    continue;
                }
                sb.append(SPLIT).append(JSONObject.toJSONString(obj));
            }
        }

        String key = String.format("resubmit", sb.toString().hashCode());

        AvoidRepeatableCommit resubmit = method.getAnnotation(AvoidRepeatableCommit.class);
        long timeout = resubmit.timeout();
        if (timeout < 0) {
            timeout = 3;
        }

        long count = redisUtils.incrBy(key, 1);
        // 设置有效期
        if (count == 1) {
            redisUtils.expire(key, timeout);
            Object object = point.proceed();
            return (CommonResult) object;
        } else {
            return ResultUtil.returnError(ResultStatus.RESUBMIT);
        }
    }



}
