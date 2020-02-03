package com.splan.base.enums.back;

public enum NoticeType {
    service_open("服务开通"),
    service_try("服务试用"),
    company_check("企业认证");

    private String name;

    NoticeType(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public static String getCName(String eName){
        for(NoticeType e:NoticeType.values()){
            if(e.toString().equals(eName))
                return e.getName();
        }
        return "";
    }

}
