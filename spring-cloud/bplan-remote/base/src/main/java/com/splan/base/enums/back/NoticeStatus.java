package com.splan.base.enums.back;

public enum NoticeStatus {

    Release("发布中"),
    Rough("草稿"),
    del("已删除");

    private String name;

    NoticeStatus(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public static String getCName(String eName){
        for(NoticeStatus e:NoticeStatus.values()){
            if(e.toString().equals(eName))
                return e.getName();
        }
        return "";
    }

}
