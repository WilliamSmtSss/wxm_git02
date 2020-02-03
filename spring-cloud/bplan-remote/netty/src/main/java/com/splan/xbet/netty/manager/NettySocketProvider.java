package com.splan.xbet.netty.manager;

import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * netty socket manager
 */
public class NettySocketProvider {

    private static final Map<String, Set<SocketChannel>> MAP = new ConcurrentHashMap<>(16);


    public static void put(String group, SocketChannel socketChannel) {
        remove(socketChannel);
        if (MAP.get(group) == null){
            Set<SocketChannel> socketChannelEntitySet = new HashSet<>();
            socketChannelEntitySet.add(socketChannel);
            MAP.put(group,socketChannelEntitySet);
        }else {
            MAP.get(group).add(socketChannel);

        }
    }


    public static Map<String, Set<SocketChannel>> getMAP() {
        return MAP;
    }

    public static Set<SocketChannel> getSet(String group){
        return MAP.get(group);
    }

    public static void remove(SocketChannel socketChannel) {
        MAP.forEach((k,v) -> v.remove(socketChannel));
    }

    public static Integer broadCast(String group,String msg){
        if (MAP.get(group)!=null){
            MAP.get(group).forEach(socketChannel -> socketChannel.writeAndFlush(new TextWebSocketFrame(msg)));
            return MAP.get(group).size();
        }


        return 0;
    }
}
