package com.splan.bplan.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.splan.base.enums.ResultStatus;
import com.splan.bplan.http.CommonResult;
import com.splan.bplan.http.DataResult;
import com.splan.bplan.interceptor.MyOkHttpRetryInterceptor;
import com.splan.bplan.result.OperateOrderResult;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class OKHttpUtil {

    /**
     * 发起get请求
     *
     * @param url
     * @return
     */
    public static String httpGet(String url) {
        String result = null;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 发送httppost请求
     *
     * @param url
     * @param data  提交的参数为key=value&key1=value1的形式
     * @return
     */
    public static DataResult httpPost(String url, String data, String authorization, String apiVersion) {
        OkHttpClient httpClient = new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS)//设置连接超时时间
                .readTimeout(10, TimeUnit.SECONDS)//设置读取超时时间
                .build();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), data);
        Request request = new Request.Builder().url(url).addHeader("Authorization",authorization).addHeader("Api-Version",apiVersion).
                post(requestBody).build();
        try {
            Response response = httpClient.newCall(request).execute();
            log.info(data);
            if (response.isSuccessful() || response.code()==201){
                String results = response.body().string();
                log.info(results);
                return new DataResult(response.code(),results,true);
            }else {
                String results = response.body().string();
                log.error(results);
                return new DataResult(500,results,false);
            }

        } catch (IOException e) {
            log.error(e.toString());
            return new DataResult(500,"",false);

        }
    }




    public static DataResult httpPostByUrlencoded(String url, Map<String, String> data) {
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
                return new DataResult(response.code(),results,true);
            } else if (response.code() == 302) {
                String location  = response.header("LOCATION");
                log.info(location);
                return new DataResult(200, location, true);
            } else {
                String results = response.body().string();
                return new DataResult(500,results,false);
            }

        } catch (IOException e) {
            log.error(e.toString());
            return new DataResult(500,"",false);
        }
    }



    /**
     * 发送httppost请求
     *
     * @param url
     * @param data  提交的参数为key=value&key1=value1的形式
     * @return
     */
    public static <T> CommonResult<T> httpPostOperate(String url, String data,boolean retry) {
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
    }

    public static void main(String[] args) {
        String result = " {\n" +
                "  \"success\": false,\n" +
                "  \"message\": \"余额不足\",\n" +
                "  \"data\": {\n" +
                "      \"extraId\":\"\",\n" +
                "    \"orderno\":\"\",\n" +
                "    \"coin\": 1000\n" +
                "  },\n" +
                "  \"code\": \"2000\"\n" +
                "}";
        /*CommonResult<OperateOrderResult> commonResult = JSON.parseObject(result, new TypeReference<CommonResult<OperateOrderResult>>(){});
        System.out.println(commonResult.getData().getCoin());
        System.out.println(commonResult);*/
        httpPostOperate("https://www.baidu.com",result,true);
    }

}
