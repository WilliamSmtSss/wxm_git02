package com.splan.xbet.netty.initializer;

import com.splan.xbet.netty.handler.WebSocketHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

public class MyChannelInitializer extends ChannelInitializer {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
//        pipeline.addLast("http-codec", new HttpServerCodec()); // Http消息编码解码
//        pipeline.addLast("aggregator", new HttpObjectAggregator(65536)); // Http消息组装
//        pipeline.addLast("http-chunked", new ChunkedWriteHandler()); // WebSocket通信支持
//        pipeline.addLast("handler", new MyChannelHandler());
//        pipeline.addLast(new IdleStateHandler(5,0,0))
//                .addLast(new HeartbeatDecoder())
//                .addLast(new HeartBeatSimpleHandler());

//        pipeline
//                //五秒没有收到消息 将IdleStateHandler 添加到 ChannelPipeline 中
//                .addLast(new IdleStateHandler(30, 0, 0))
//                .addLast(new HeartbeatDecoder())
//                .addLast(new HeartBeatSimpleHandler());

//        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
//        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
//
//        pipeline.addLast(new MyChannelHandler());
        pipeline.addLast("http-codec", new HttpServerCodec())
                .addLast("http-chunked", new ChunkedWriteHandler())
                .addLast("aggregator", new HttpObjectAggregator(65536))
                .addLast("handler",new WebSocketHandler());






    }
}
