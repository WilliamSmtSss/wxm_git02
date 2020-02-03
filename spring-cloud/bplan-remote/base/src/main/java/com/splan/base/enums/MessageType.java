package com.splan.base.enums;

public enum MessageType {

    REGISTER("REGISTER"),FORGET("FORGET");

    MessageType(String name){
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }


}
