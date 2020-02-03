//package com.splan.bplan.netty.handler;
//
//import com.splan.bplan.netty.manager.NettySocketHolder;
//import com.splan.bplan.netty.pojo.CustomProtocol;
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.SimpleChannelInboundHandler;
//import io.netty.channel.socket.nio.NioSocketChannel;
//import io.netty.handler.timeout.IdleState;
//import io.netty.handler.timeout.IdleStateEvent;
//import io.netty.util.CharsetUtil;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public class HeartBeatSimpleHandler extends SimpleChannelInboundHandler<CustomProtocol> {
//
//    //private ChannelHandlerContext ctx;
//
//
//    private static final ByteBuf HEART_BEAT =  Unpooled.unreleasableBuffer(Unpooled.copiedBuffer(new CustomProtocol(123456L,"pong").toString(), CharsetUtil.UTF_8));
//
//
//    /**
//     * 取消绑定
//     * @param ctx
//     * @throws Exception
//     */
//    @Override
//    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//
//        NettySocketHolder.remove((NioSocketChannel) ctx.channel());
//    }
//
//    @Override
//    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//
//        if (evt instanceof IdleStateEvent){
//            IdleStateEvent idleStateEvent = (IdleStateEvent) evt ;
//
//            if (idleStateEvent.state() == IdleState.READER_IDLE){
//                log.info("已经30秒没有收到信息！");
//                //向客户端发送消息
//                //ctx.writeAndFlush(HEART_BEAT).addListener(ChannelFutureListener.CLOSE_ON_FAILURE) ;
//            }
//
//
//        }
//
//        super.userEventTriggered(ctx, evt);
//    }
//
//
//
//    @Override
//    protected void channelRead0(ChannelHandlerContext channelHandlerContext, CustomProtocol customProtocol) throws Exception {
//
//        log.info("收到customProtocol={}", customProtocol);
//
//        //保存客户端与 Channel 之间的关系
//        NettySocketHolder.put(customProtocol.getId(),(NioSocketChannel)channelHandlerContext.channel()) ;
//
//
//    }
//}
