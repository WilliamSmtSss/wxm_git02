package com.splan.base.enums;


public enum KaliPayType {
    AliPay("支付宝","AliPay"),
    WeChat( "微信","WeChat"),
    VISA("银联","VISA"),
    AliPayH5("支付宝唤醒","AliPayH5"),
    BANK("银行","BANK");
    private String name;
    private  String enName;

    KaliPayType(String name,String enName) {
        this.name = name;
        this.enName=enName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }
}
