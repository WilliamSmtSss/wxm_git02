package com.splan.bplan.thread;

import com.splan.bplan.netty.manager.NettySocketHolder;

import java.util.concurrent.Callable;

/**
 * 消息广播
 */
public class MsgCallable implements Callable<String> {

    private String group;

    private String msg;

    private String type;

    public MsgCallable(String group,String type,String msg){
        this.group = group;
        this.msg = msg;
        this.type = type;
    }

    @Override
    public String call() throws Exception {
        //int x = NettySocketProvider.broadCast(group,type+"-"+msg);
        int x = NettySocketHolder.broadCast(msg);
        return "已广播"+x+"个在线用户";
    }

}
