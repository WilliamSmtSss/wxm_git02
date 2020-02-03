package com.splan.bplan.netty.mq.exchange;

import com.splan.bplan.netty.mq.config.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(Object message) {
        log.info("【消息发送者】发送消息到fanout交换机，消息内容为: {}", message);
        rabbitTemplate.convertAndSend(Constants.FANOUT_EXCHANGE_NAME, "", message);
    }

}
