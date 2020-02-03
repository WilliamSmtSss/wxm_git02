package com.splan.xbet.netty.handler;

import com.splan.xbet.netty.manager.NettySocketHolder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class WebSocketHandler extends SimpleChannelInboundHandler<Object> {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final AtomicInteger response = new AtomicInteger();

    private WebSocketServerHandshaker handshaker;


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        NettySocketHolder.put(ctx.channel().id(),(SocketChannel)ctx.channel());
        //NettySocketProvider.put("default",(SocketChannel)ctx.channel());
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        NettySocketHolder.remove((SocketChannel)ctx.channel());
       // NettySocketProvider.remove((SocketChannel)ctx.channel());
        super.channelInactive(ctx);
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        //普通HTTP接入
        if(msg instanceof FullHttpRequest){
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        }else if(msg instanceof WebSocketFrame){ //websocket帧类型 已连接
            //BinaryWebSocketFrame CloseWebSocketFrame ContinuationWebSocketFrame
            //PingWebSocketFrame PongWebSocketFrame TextWebScoketFrame
            handleWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request){
        //如果http解码失败 则返回http异常 并且判断消息头有没有包含Upgrade字段(协议升级)
        if(!request.getDecoderResult().isSuccess()
                || (!"websocket".equals( request.headers().get("Upgrade")))    ){
            sendHttpResponse(ctx, request, new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return ;
        }
        //构造握手响应返回
        WebSocketServerHandshakerFactory ws = new WebSocketServerHandshakerFactory("", null, false);
        handshaker = ws.newHandshaker(request);
        if(handshaker == null){
            //版本不支持
            WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
        }else{
            handshaker.handshake(ctx.channel(), request);
        }
    }
    /**
     * websocket帧
     * @param ctx
     * @param frame
     */
    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame){
        //判断是否关闭链路指令
        if(frame instanceof CloseWebSocketFrame){
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return ;
        }
        //判断是否Ping消息 -- ping/pong心跳包
        if(frame instanceof PingWebSocketFrame){
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return ;
        }
        //本程序仅支持文本消息， 不支持二进制消息
        if(!(frame instanceof TextWebSocketFrame)){
            throw new UnsupportedOperationException(
                    String.format("%s frame types not supported", frame.getClass().getName()));
        }

        //返回应答消息 text文本帧
        String request = ((TextWebSocketFrame) frame).text();
        //打印日志

        log.debug(String.format("%s received %s", ctx.channel(), request));

        //发送到客户端websocket
        /*ctx.channel().write(new TextWebSocketFrame(request
                + ", 欢迎使用Netty WebSocket服务， 现在时刻:"
                + new java.util.Date().toString()));

        //发送到客户端websocket
        ctx.channel().write(new TextWebSocketFrame("made"));*/
        if (request.equals("1")){
            ctx.channel().write(new TextWebSocketFrame("2"));//发送心跳
        }else {
            log.debug(String.format("error msg %s received %s", ctx.channel(), request));
        }
        /*else if (request.contains("default")){
            NettySocketProvider.put("default",(SocketChannel)ctx.channel());
        }else if(request.contains("dataId")){
            NettySocketProvider.put(request,(SocketChannel)ctx.channel());
        } else {
            log.info(String.format("error msg %s received %s", ctx.channel(), request));
        }*/

    }

    /**
     * response
     * @param ctx
     * @param request
     * @param response
     */
    private static void sendHttpResponse(ChannelHandlerContext ctx,
                                         FullHttpRequest request, FullHttpResponse response){
        //返回给客户端
        if(response.getStatus().code() != HttpResponseStatus.OK.code()){
            ByteBuf buf = Unpooled.copiedBuffer(response.getStatus().toString(), CharsetUtil.UTF_8);
            response.content().writeBytes(buf);
            buf.release();
            HttpHeaders.setContentLength(response, response.content().readableBytes());
        }
        //如果不是keepalive那么就关闭连接
        ChannelFuture f = ctx.channel().writeAndFlush(response);
        if(!HttpHeaders.isKeepAlive(response)
                || response.getStatus().code() != HttpResponseStatus.OK.code()){
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    /**
     * 异常 出错
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }



}
