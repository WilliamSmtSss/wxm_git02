package com.splan.xbet.netty.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ExchangeConfiguration {

    /**
     * 创建广播形式的交换机
     *
     * @return 交换机实例
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        log.info("【【【交换机实例创建成功】】】");
        return new FanoutExchange("fanout.exchange.name");
    }

    /**
     * 测试队列一
     *
     * @return 队列实例
    @Bean
    public Queue queue1() {
        log.info("【【【测试队列一实例创建成功】】】");
        return new Queue(Constants.TEST_QUEUE1_NAME);
    }

    *//**
     * 测试队列二
     *
     * @return 队列实例
     *//*
    @Bean
    public Queue queue2() {
        log.info("【【【测试队列二实例创建成功】】】");
        return new Queue(Constants.TEST_QUEUE2_NAME);
    }

    *//**
     * 绑定队列一到交换机
     *
     * @return 绑定对象
     *//*
    @Bean
    public Binding bingQueue1ToExchange() {
        log.info("【【【绑定队列一到交换机成功】】】");
        return BindingBuilder.bind(queue1()).to(fanoutExchange());
    }

    *//**
     * 绑定队列二到交换机
     *
     * @return 绑定对象
     *//*
    @Bean
    public Binding bingQueue2ToExchange() {
        log.info("【【【绑定队列二到交换机成功】】】");
        return BindingBuilder.bind(queue2()).to(fanoutExchange());
    }*/

}
