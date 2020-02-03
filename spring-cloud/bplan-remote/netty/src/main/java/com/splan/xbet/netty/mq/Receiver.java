package com.splan.xbet.netty.mq;

import com.splan.xbet.netty.manager.NettySocketHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RabbitListener(queues = "${rabbitmq.queue}")
public class Receiver {

    @RabbitHandler
    public void receiveMessage(@Payload String body) {
        //log.info("消息接收者接收到来自【队列一】的消息，消息内容: {}", body);
        Integer x = NettySocketHolder.broadCast(body);
        //log.info("已广播"+x+"用户");
    }
}
