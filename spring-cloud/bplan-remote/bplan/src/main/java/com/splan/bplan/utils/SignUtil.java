package com.splan.bplan.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.*;

@Slf4j
public class SignUtil {

    public static Map<String,Object> convertObjToMap(Object obj){
        Map<String,Object> reMap = new HashMap<String,Object>();
        if (obj == null)
            return null;
        Field[] fields = obj.getClass().getDeclaredFields();
        try {
            for(int i=0;i<fields.length;i++){
                try {
                    Field f = obj.getClass().getDeclaredField(fields[i].getName());
                    f.setAccessible(true);
                    Object o = f.get(obj);
                    reMap.put(fields[i].getName(), o);
                } catch (NoSuchFieldException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return reMap;
    }

    public static String getSign(Object params, String secret){
        Map<String, Object> pp = convertObjToMap(params);
        return getSign(pp,secret);
    }

    public static String getSign(Map<String, Object> params, String secret)
    {
        String sign="";
        StringBuffer sb = new StringBuffer();
        //step1：先对请求参数排序
        Set<String> keySet = params.keySet();
        TreeSet<String> sortSet = new TreeSet<>();
        sortSet.addAll(keySet);
        Iterator<String> it = sortSet.iterator();
        //step2：把参数的key value链接起来 secretkey放在最后面，得到要加密的字符串
        while(it.hasNext())
        {
            String key = it.next();
            if (ObjectUtils.allNotNull(params.get(key))){
                String value = params.get(key).toString();
                sb.append(key).append("=").append(value).append("&");
            }

        }
        if (sb.length()>0){
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append(secret);
        log.info(sb.toString());
        byte[] md5Digest;
        try {
            //得到Md5加密得到sign
            md5Digest = getMD5Digest(sb.toString());
            sign = byte2hex(md5Digest);
        } catch (IOException e) {
            log.error("生成签名错误",e);
        }
        return sign;
    }

    private static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
            //sign.append(hex.toLowerCase());
        }
        return sign.toString();
    }

    private static byte[] getMD5Digest(String data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            bytes = md.digest(data.getBytes("UTF-8"));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse);
        }
        return bytes;
    }


}
