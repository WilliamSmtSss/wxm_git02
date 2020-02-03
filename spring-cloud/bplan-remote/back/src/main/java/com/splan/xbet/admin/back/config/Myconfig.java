package com.splan.xbet.admin.back.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application")
public class Myconfig {
    private String version;
    private static String profile;
    private static String sessionExpire;
    private static String bannerMax;
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public static String getProfile() {
        return profile;
    }
    public void setProfile(String profile) {
        this.profile = profile;
    }

    public static String getSessionExpire() {
        return sessionExpire;
    }

    public void setSessionExpire(String sessionExpire) {
        this.sessionExpire = sessionExpire;
    }

    public static String getBannerMax() {
        return bannerMax;
    }

    public  void setBannerMax(String bannerMax) {
        this.bannerMax = bannerMax;
    }
}
