package com.splan.xbet.frontback.frontback.contantes;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "s3")
public class Consts {

    private static  String accessKey="";
    private static  String secretKey="";
    private static  String bucketName="";

    public static String getAccessKey() {
        return accessKey;
    }

    public  void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public static String getSecretKey() {
        return secretKey;
    }

    public  void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public static String getBucketName() {
        return bucketName;
    }

    public  void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
