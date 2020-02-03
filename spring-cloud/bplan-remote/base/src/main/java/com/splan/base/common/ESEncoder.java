package com.splan.base.common;

import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.LoggerContextVO;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ESEncoder extends PatternLayoutEncoder {

    @Override
    public byte[] encode(ILoggingEvent event) {
        return convertEventWithError(event).getBytes();
    }

    private String convertEventWithError(ILoggingEvent event) {
        try {
            // 检查-------------------------------------------------------
            if (event == null)
                return "";
            if (StringUtils.isBlank(event.getMessage()))
                return "";
            // 获取源Msg替换结果-------------------------------------------
            String msg = printReplace(event.getMessage(), event.getArgumentArray());
            // 尝试获取错误日志--------------------------------------------
            IThrowableProxy err = event.getThrowableProxy();
            StringBuilder errStr = null;
            if (err != null) {
                errStr = new StringBuilder();
                errStr.append(err.getClassName());
                errStr.append(" : ");
                errStr.append(err.getMessage());
                errStr.append(" \n ");
                StackTraceElementProxy[] errTrack = err.getStackTraceElementProxyArray();
                for (StackTraceElementProxy stackTraceElementProxy : errTrack) {
                    errStr.append(stackTraceElementProxy.getStackTraceElement());
                    errStr.append(" \n ");
                }
                errStr.delete(errStr.length() - 3, errStr.length());
            }
            // 构建Json--------------------------------------------------
            LinkedHashMap<String, String> result = new LinkedHashMap();
            LoggerContextVO context = event.getLoggerContextVO();
            result.put("serverName", context == null ? "default" : context.getName());
            result.put("logTime", LocalDateTime.now().toString() + "Z");
            result.put("level", event.getLevel().levelStr);
            result.put("thread", event.getThreadName());
            result.put("logger", event.getLoggerName());
            result.put("msg", msg);
            if (errStr != null && errStr.length() != 0)
                result.put("err", errStr.toString());
            // 返回--------------------------------------------------
            return (new ObjectMapper()).writeValueAsString(result) + "\n";
        } catch (Throwable e) {
            try {
                return (new ObjectMapper()).writeValueAsString(ImmutableMap.of("err", "ESEncoder日志工具错误:" + e.toString())) + "\n";
            } catch (Throwable e2) {
                return "{\"err\":\"ESEncoder日志工具错误\"}\n";
            }
        }
    }

    public static String printReplace(String first, Object... replaces) {
        try {
            int replaceLen = 0;
            if (StringUtils.isBlank(first))
                return first;
            if (replaces == null || (replaceLen = replaces.length) == 0)
                return first;

            StringBuilder result = new StringBuilder();
            int replaceIdx = 0;
            int curCpIdx = 0;

            Matcher m = Pattern.compile("\\{\\}").matcher(first);
            while (m.find()) {
                if (replaceIdx < replaceLen) {
                    result.append(first.substring(curCpIdx, m.start()));
                    result.append(replaces[replaceIdx] == null ? "null" : replaces[replaceIdx].toString());
                    curCpIdx = m.end();
                } else {
                    result.append(first.substring(curCpIdx, first.length()));
                    break;
                }
                replaceIdx++;
            }
            result.append(first.substring(curCpIdx, first.length()));

            return result.toString();
        } catch (Throwable e) {
            return first;
        }
    }
}
