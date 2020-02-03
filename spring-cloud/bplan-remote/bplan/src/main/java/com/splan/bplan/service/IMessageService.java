package com.splan.bplan.service;

import com.splan.base.enums.MessageType;
import com.splan.bplan.http.CommonResult;

public interface IMessageService {

    CommonResult<String> sendMessage(String mobileArea,String mobile, MessageType messageType);
}
