package com.splan.bplan.utils;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.splan.bplan.http.SeriesGameNotify;

import java.io.IOException;
import java.util.List;

public class JsonUtil {

    public static <T> T stringToObject(String string,Class<T> clazz){

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Object object = null;
        try {
            object = mapper.readValue(string,clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (T) object;
    }

    public static <T> List<T> stringToListObject(String string, Class<T> clazz){


        /*ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Object object = null;
        try {
            object = mapper.readValue(string,clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return JSONObject.parseArray(string,clazz);
    }


    public static void main(String[] args) {
        String source = "{\"data\":{\"stage\":\"\",\"bo\":3,\"status\":\"ongoing\",\"start_time\":\"2018-12-09T11:00:00.000+08:00\",\"end_time\":\"\",\"game_id\":1,\"category\":\"\",\"league\":{\"id\":251,\"name\":\"ESL One卡托维兹站预选赛\",\"region\":0,\"status\":\"unstart\",\"start_time\":\"2018-12-08T00:00:00.000+08:00\",\"game_id\":1,\"level\":0},\"teams\":[{\"id\":1398,\"abbr\":\"coL\",\"name\":\"compLexity Gaming\",\"logo\":\"http://rw-assets.oss-cn-hangzhou.aliyuncs.com/teams/c2/4f/ff/61-13a4-46ce-927d-9ae261d98e2f\",\"country\":\"us\",\"region\":\"north america\"},{\"id\":1918,\"abbr\":\"test321\",\"name\":\"test 321\",\"logo\":\"http://rw-assets.oss-cn-hangzhou.aliyuncs.com/teams/1f/83/61/69-1996-4d11-b692-5a6d02bb508c\",\"country\":\"ph\",\"region\":\"south america\"}],\"campaigns\":[{\"id\":16,\"number\":1},{\"id\":17,\"number\":2},{\"id\":18,\"number\":3}],\"scores\":[{\"team_id\":2627,\"score\":0,\"sequence\":1},{\"team_id\":2628,\"score\":0,\"sequence\":2}]},\"encrypted\":\"y2WFh9/mDHSPXKVT+YtMYjdzogrtllKQu6z/AVXssVFtsLju10uLNmH2hY/CVJVgnVzDh9lWRcEZOiw8st7oue/6QUU6xj0LnMxUXrooA7yI/H/GudfuUsuEUJOOGdGS9UIzTSU6c/Ag8BUdq83Fh717T0R34Q16l8AG9tyLRy4=\"}";
        //SeriesGameNotify seriesGameNotify = JsonUtil.stringToObject(source, SeriesGameNotify.class);
        SeriesGameNotify seriesGameNotify = JsonUtil.stringToObject(source,SeriesGameNotify.class);
        System.out.println(seriesGameNotify);
    }
}
