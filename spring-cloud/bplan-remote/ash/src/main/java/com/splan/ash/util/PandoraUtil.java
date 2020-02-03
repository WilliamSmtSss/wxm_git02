package com.splan.ash.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.splan.ash.constant.MapKeyComparator;
import com.splan.base.bean.ash.AshBean;
import com.splan.base.http.PandoraListResult;
import com.splan.base.http.PandoraResult;
import com.splan.base.utils.MD5Util;
import com.splan.base.utils.RSASignature;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class PandoraUtil {


    private static String key;

    private static String tenantId;

    private static String baseUrl;

    public static String getBaseUrl() {
        return baseUrl;
    }

    @Value("${pandora.datacenter.baseurl}")
    public void setBaseUrl(String baseUrl) {
        PandoraUtil.baseUrl = baseUrl;
    }

    public static String getTenantId() {
        return tenantId;
    }

    @Value("${pandora.tenantId}")
    public void setTenantId(String tenantId) {
        PandoraUtil.tenantId = tenantId;
    }

    public static String getKey() {
        return key;
    }

    @Value("${pandora.encrypt.privatekey}")
    public void setKey(String key) {
        PandoraUtil.key = key;
    }

    /**
     * 发起get请求
     *
     * @param url
     * @return
     */
    public static String httpGet(String url,Map<String, Object> data) {
        String result = null;
        OkHttpClient client = new OkHttpClient();
        ZoneOffset zoneOffset = ZoneOffset.ofHours(8);
        LocalDateTime localDateTime = LocalDateTime.now();
        long requestTime = localDateTime.toEpochSecond(zoneOffset);
        data.put("tenant_id",getTenantId());
        data.put("request_time", requestTime);
        String sign = getSign(data);
        Request request = new Request.Builder().url(baseUrl+url+"?"+appendParam(data))
                .addHeader("Sign",sign)
                .build();
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
            //System.out.println(result);
        } catch (Exception e) {
            //e.printStackTrace();
            log.error(e.toString());
        }
        return result;
    }

    public static <T extends AshBean> PandoraListResult<T> httpListGet(String url, Map<String, Object> data, Class clazz) {
        String resultStr = httpGet(url,data);
        log.info(resultStr);
        if (StringUtils.isNotBlank(resultStr)){
            PandoraListResult<T> result = JSON.parseObject(resultStr,new TypeReference<PandoraListResult<T>>(clazz){}.getType());
            return result;
        }
        return new PandoraListResult<T>();

    }

    /**
     * 发送httppost请求
     *
     * @param url
     * @param data  提交的参数为key=value&key1=value1的形式
     * @return
     */
    public static PandoraResult httpPost(String url, String data, String tenantId, String requestTime) {
        OkHttpClient httpClient = new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS)//设置连接超时时间
                .readTimeout(10, TimeUnit.SECONDS)//设置读取超时时间
                .build();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), data);
        Request request = new Request.Builder().url(url)
                .addHeader("tenant_id",tenantId)
                .addHeader("request_time",requestTime)
                .post(requestBody).build();
        try {
            Response response = httpClient.newCall(request).execute();
            log.info(data);
            if (response.isSuccessful() || response.code()==201){
                String results = response.body().string();
                log.info(results);
                return JSON.parseObject(results,PandoraResult.class);
                //return new PandoraResult(response.code(),results,true);
            }else {
                String results = response.body().string();
                log.error(results);
                return JSON.parseObject(results,PandoraResult.class);
                //return new PandoraResult(500,results,false);
            }

        } catch (IOException e) {
            log.error(e.toString());
            return new PandoraResult(500,null,false);

        }
    }




    public static PandoraResult httpPostByUrlencoded(String url, Map<String, String> data) {
        OkHttpClient httpClient = new OkHttpClient().newBuilder().followRedirects(false).followSslRedirects(false)
                .build();

        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : data.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder().url(url).
                post(requestBody).build();
        try {
            Response response = httpClient.newCall(request).execute();

            log.info(data.toString());
            if (response.isSuccessful() || response.code()==201){
                String results = response.body().string();
                log.info(results);
                return JSON.parseObject(results,PandoraResult.class);
                //return new PandoraResult(response.code(),results,true);
            }  else {
                String results = response.body().string();
                return JSON.parseObject(results,PandoraResult.class);
                //return new PandoraResult(500,results,false);
            }

        } catch (IOException e) {
            log.error(e.toString());
            return new PandoraResult(500,null,false);
        }
    }

    public static String getSign(Map<String, Object> map){
        String param = appendParam(map);
        String content = MD5Util.crypt(param);
        return RSASignature.sign(content,getKey()).replace("\n","");
    }

    private static String appendParam(Map<String, Object> map){
        map = sortMapByKey(map);
        StringBuffer str = new StringBuffer();
        String strr = "";
        //value
        for(String key : map.keySet()){
            str.append(key+"="+map.get(key));
            str.append("&");
        }
        if (str.length()>1){
            strr = str.toString().substring(0,str.toString().length()-1);
        }
        return strr;
    }

    public static Map<String, Object> sortMapByKey(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, Object> sortMap = new TreeMap<String, Object>(
                new MapKeyComparator());

        sortMap.putAll(map);

        return sortMap;
    }



    /**
     * 发送httppost请求
     *
     * @return
     */
    /*public static <T> CommonResult<T> httpPostOperate(String url, String data, boolean retry) {
        OkHttpClient httpClient;

        if (retry){
            MyOkHttpRetryInterceptor myOkHttpRetryInterceptor = new MyOkHttpRetryInterceptor.Builder()
                    .executionCount(3)
                    .retryInterval(1000)
                    .build();
            if (url.contains("https://")) {
                httpClient = OKHttpClientBuilder.buildOKHttpClient().retryOnConnectionFailure(true)
                        .addInterceptor(myOkHttpRetryInterceptor)
                        .connectionPool(new ConnectionPool())
                        .connectTimeout(15, TimeUnit.SECONDS)
                        .readTimeout(10, TimeUnit.SECONDS)
                        .build();

            }else{
                httpClient = new OkHttpClient.Builder().retryOnConnectionFailure(true)
                        .addInterceptor(myOkHttpRetryInterceptor)
                        .connectionPool(new ConnectionPool())
                        .connectTimeout(15, TimeUnit.SECONDS)
                        .readTimeout(10, TimeUnit.SECONDS)
                        .build();
            }

        }else {
            if (url.contains("https://")) {
                httpClient = OKHttpClientBuilder.buildOKHttpClient().connectTimeout(15, TimeUnit.SECONDS)//设置连接超时时间
                        .readTimeout(10, TimeUnit.SECONDS)//设置读取超时时间
                        .build();

            }else {
                httpClient = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS)//设置连接超时时间
                        .readTimeout(10, TimeUnit.SECONDS)//设置读取超时时间
                        .build();
            }
        }



        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), data);
        Request request = new Request.Builder().url(url).
                post(requestBody).build();
        try {
            Response response = httpClient.newCall(request).execute();
            log.info(data);
            if (response.isSuccessful() || response.code()==201){
                String results = response.body().string();
                log.info(results);
                CommonResult<T> commonResult = JSON.parseObject(results, new TypeReference<CommonResult<T>>(){});
                return commonResult;
            }else {
                String results = response.body().string();
                log.error(results);
                return ResultUtil.returnError(ResultStatus.PARAM_ERROR);
            }

        } catch (IOException e) {
            log.error(e.toString());
            return ResultUtil.returnError(ResultStatus.PARAM_ERROR);

        }
    }*/
    public static void main(String[] args) {
        String url = "http://bapi.stage.risewinter.cn/api/v1/games";
        Map<String, Object> data = new HashMap<>();
        data.put("limit",10);
        data.put("offset",0);
        //data.put("request_time",);
        //data.put("tenant_id",);
        String ss = httpGet(url,data);
    }
}
