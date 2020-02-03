package com.splan.xbet.netty.manager;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NettySocketHolder {

    private static final Map<ChannelId, SocketChannel> MAP = new ConcurrentHashMap<>(16);

    public static void put(ChannelId id, SocketChannel socketChannel) {
        MAP.put(id, socketChannel);
    }

    public static Channel get(ChannelId id) {
        return MAP.get(id);
    }

    public static Map<ChannelId, SocketChannel> getMAP() {
        return MAP;
    }

    public static void remove(SocketChannel nioSocketChannel) {
        MAP.entrySet().stream().filter(entry -> entry.getValue() == nioSocketChannel).forEach(entry -> MAP.remove(entry.getKey()));
    }

    public static Integer broadCast(String msg){
        int x = 0;
        for (Map.Entry<ChannelId, SocketChannel> entry : MAP.entrySet()) {

            entry.getValue().writeAndFlush(new TextWebSocketFrame(msg));
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            x++;
        }
        return x;
    }

    public static Integer getOnLineUserNow(){
        return MAP.size();
    }

}
