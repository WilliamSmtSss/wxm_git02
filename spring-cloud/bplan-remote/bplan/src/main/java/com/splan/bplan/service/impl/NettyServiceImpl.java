package com.splan.bplan.service.impl;

import com.splan.bplan.netty.mq.exchange.MessageSender;
import com.splan.bplan.service.INettyService;
import com.splan.bplan.thread.MsgCallable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
@Service
public class NettyServiceImpl implements INettyService {

    private ExecutorService service = Executors.newFixedThreadPool(100);

    @Autowired
    private MessageSender messageSender;

    @Override
    public String broadCastMessage(String message) {
        /*MsgCallable msgCallable = new MsgCallable("","",message);
        Future<String> futureTask = service.submit(msgCallable);
        try {
            log.info(futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/
        messageSender.sendMessage(message);
        return null;
    }

    @Override
    public String broadCastMessageByGroup(String group,String type, String message) {
        MsgCallable msgCallable = new MsgCallable(group,type,message);
        Future<String> futureTask = service.submit(msgCallable);
        try {
            log.info(futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
