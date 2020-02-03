package com.splan.xbet.back.backforbusiness.Interceptors;

import com.splan.base.enums.ResultStatus;
import com.splan.base.utils.ResultUtil;
import com.splan.xbet.back.backforbusiness.annotation.Delay;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class DelayInterceptor extends HandlerInterceptorAdapter {
    private long lastTime = 0;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception { //开始进入请求地址拦截
        HandlerMethod hm = (HandlerMethod) o;
        Delay delay = hm.getMethodAnnotation(Delay.class);
        //最多导出一个月的订单
//        if(StringUtils.isNotBlank(httpServletRequest.getParameter("export"))){
//            String startDate=httpServletRequest.getParameter("startDate");
//            String endDate=httpServletRequest.getParameter("endDate");
//            if(StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate)){
////                ResultUtil.sendJsonMessage(httpServletResponse,ResultUtil.returnError(ResultStatus.INOUT_DATE));
//                ResultUtil.sendHtmlMessage(httpServletResponse, ResultStatus.INOUT_DATE.getMessage());
//                return false;
//            }
//            if(DateUtil.differentDays(DateUtil.stringToDate2(startDate),DateUtil.stringToDate2(endDate))>31){
////                ResultUtil.sendJsonMessage(httpServletResponse,ResultUtil.returnError(ResultStatus.OUT_ONE_MONTH));
//                ResultUtil.sendHtmlMessage(httpServletResponse,ResultStatus.OUT_ONE_MONTH.getMessage());
//                return false;
//            }
//        }
        if (delay != null && StringUtils.isNotBlank(httpServletRequest.getParameter("export"))) {
            if(!startDelay(delay.time())){
                ResultUtil.sendJsonMessage(httpServletResponse,ResultUtil.returnError(ResultStatus.CLICK_FAST));
                return false;
            }
        }
        return true;
    }
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception { //处理请求完成后视图渲染之前的处理操作
    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception { //视图渲染之后的操作
    }

    private boolean startDelay(int time) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime > time) {
            lastTime = currentTime;
            return true;
        }
        return false;
    }

}
