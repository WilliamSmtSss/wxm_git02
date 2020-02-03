package com.splan.xbet.admin.back.dto;

import lombok.Data;

@Data
public class VersionDto {

//    private String appDownloadUrl;  //下载地址
//    private String appName; //应用名称
//    private float appSize; //引用大小
//    private String introduction; //引用简介简介
//    private String packageName; //app包名字，例如 BAOFENG_Android_app_v0.0.1.apk
//    private int status;//是否强制更新 0 否 1是
    private String terminal; //终端类型 0 安卓 1 IOS
    //private String version; //版本号 例如：0.0.1 、0.0.2、1.0.3
    private int versionCode; //版本 每次配置自增 1,2,3,4,5...
}
