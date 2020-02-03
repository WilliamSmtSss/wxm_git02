package com.splan.base.enums;

public enum HyPayType {
    AliPay("ALI_QRCODE", "支付宝"),
    VISA("BANK_QRCODE", "银联");

    private String channel;
    private String name;

    HyPayType(String channel, String name) {
        this.channel = channel;
        this.name = name;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
