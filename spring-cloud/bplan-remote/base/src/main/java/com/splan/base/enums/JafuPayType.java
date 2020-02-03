package com.splan.base.enums;

public enum JafuPayType {
    BOC(963, "中国银行"),
    ABC(964, "中国农业银行"),
    CCB(965, "中国建设银行"),
    ICBC(967, "中国工商银行"),
    BCM(981, "交通银行"),
    AliPay(992, "支付宝"),
    WeChat(1004, "微信");


    private int code;
    private String name;

    JafuPayType(int code, String name) {
        this.name = name;
        this.code = code;
    }

    public static String getName(int code) {
        for (JafuPayType jf : JafuPayType.values()) {
            if (jf.getCode() == code) {
                return jf.getName();
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
