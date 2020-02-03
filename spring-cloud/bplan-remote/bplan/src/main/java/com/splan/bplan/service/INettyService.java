package com.splan.bplan.service;

public interface INettyService {

    String broadCastMessage(String message);

    String broadCastMessageByGroup(String group,String type,String message);
}
