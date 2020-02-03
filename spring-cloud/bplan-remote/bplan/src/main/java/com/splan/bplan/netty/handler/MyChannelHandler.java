//package com.splan.bplan.netty.handler;
//
//import com.splan.bplan.netty.utils.SpringUtil;
//import com.splan.bplan.service.IWebSocketService;
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.ChannelFuture;
//import io.netty.channel.ChannelFutureListener;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.SimpleChannelInboundHandler;
//import io.netty.handler.codec.http.*;
//import io.netty.handler.codec.http.websocketx.*;
//import io.netty.handler.timeout.IdleState;
//import io.netty.handler.timeout.IdleStateEvent;
//import io.netty.util.CharsetUtil;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public class MyChannelHandler extends SimpleChannelInboundHandler<Object> {
//
//    private WebSocketServerHandshaker handshaker;
//    private ChannelHandlerContext ctx;
//
//    private static IWebSocketService webSocketService;
//
//    static {
//        webSocketService = SpringUtil.getBean(IWebSocketService.class);
//    }
//
//
//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        ctx.flush();
//    }
//
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        ctx.close();
//    }
//
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//
//        System.out.println("RamoteAddress : " + ctx.channel().remoteAddress() + " active !");
//
//  //        ctx.channel().writeAndFlush( "Welcome to " + InetAddress.getLocalHost().getHostName() + " service!\n");
//        ctx.channel().writeAndFlush("connect success");
//        super.channelActive(ctx);
//    }
//
//
//
//    /**
//     * 处理Http请求，完成WebSocket握手<br/>
//     * 注意：WebSocket连接第一次请求使用的是Http
//     * @param ctx
//     * @param request
//     * @throws Exception
//     */
//    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
//        // 如果HTTP解码失败，返回HTTP异常
//        if (!request.getDecoderResult().isSuccess() || (!"websocket".equals(request.headers().get("Upgrade")))) {
//            sendHttpResponse(ctx, request, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
//            return;
//        }
//
//        // 正常WebSocket的Http连接请求，构造握手响应返回
//        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws://" + request.headers().get(HttpHeaders.Names.HOST), null, false);
//        handshaker = wsFactory.newHandshaker(request);
//        if (handshaker == null) { // 无法处理的websocket版本
//            WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
//        } else { // 向客户端发送websocket握手,完成握手
//            handshaker.handshake(ctx.channel(), request);
//            // 记录管道处理上下文，便于服务器推送数据到客户端
//            //sendWebSocket("init====");
//            this.ctx = ctx;
//            sendWebSocket("init====");
//        }
//    }
//
//    /**
//     * Http返回
//     * @param ctx
//     * @param request
//     * @param response
//     */
//    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest request, FullHttpResponse response) {
//        // 返回应答给客户端
//        if (response.getStatus().code() != 200) {
//            ByteBuf buf = Unpooled.copiedBuffer(response.getStatus().toString(), CharsetUtil.UTF_8);
//            response.content().writeBytes(buf);
//            buf.release();
//            HttpHeaders.setContentLength(response, response.content().readableBytes());
//        }
//
//        // 如果是非Keep-Alive，关闭连接
//        ChannelFuture f = ctx.channel().writeAndFlush(response);
//        if (!HttpHeaders.isKeepAlive(request) || response.getStatus().code() != 200) {
//            f.addListener(ChannelFutureListener.CLOSE);
//        }
//    }
//
//    /**
//     * 处理Socket请求
//     * @param ctx
//     * @param frame
//     * @throws Exception
//     */
//    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
//        // 判断是否是关闭链路的指令
//        if (frame instanceof CloseWebSocketFrame) {
//            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
//            return;
//        }
//        // 判断是否是Ping消息
//        if (frame instanceof PingWebSocketFrame) {
//            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
//            return;
//        }
//        // 当前只支持文本消息，不支持二进制消息
//        if ((frame instanceof TextWebSocketFrame)) {
//            //获取发来的消息
//            String text =((TextWebSocketFrame)frame).text();
//            System.out.println("mage : " + text);
//            String returnMsg = webSocketService.handlerMessage(text);
//            sendWebSocket(returnMsg);
//
//        } else {
//            System.err.println("------------------error--------------------------");
//        }
//    }
//
//    /**
//     * WebSocket返回
//     */
//    public void sendWebSocket(String msg) throws Exception {
//        if (this.handshaker == null || this.ctx == null || this.ctx.isRemoved()) {
//            throw new Exception("尚未握手成功，无法向客户端发送WebSocket消息");
//        }
//        //发送消息
//        this.ctx.channel().write(new TextWebSocketFrame(msg));
//        this.ctx.flush();
//    }
//
//    @Override
//    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//
//        if (evt instanceof IdleStateEvent){
//            IdleStateEvent idleStateEvent = (IdleStateEvent) evt ;
//
//            if (idleStateEvent.state() == IdleState.WRITER_IDLE){
//                log.info("已经 10 秒没有发送信息！");
//                //向服务端发送消息
////                CustomProtocol heartBeat = SpringBeanFactory.getBean("heartBeat", CustomProtocol.class);
////                ctx.writeAndFlush(heartBeat).addListener(ChannelFutureListener.CLOSE_ON_FAILURE) ;
//            }
//
//
//        }
//
//        super.userEventTriggered(ctx, evt);
//    }
//
//    @Override
//    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
//        log.info("收到customProtocol={}", o);
//        ctx.channel().writeAndFlush("yes server already accept your message" + o);
//        ctx.close();
//        //保存客户端与 Channel 之间的关系
//        //NettySocketHolder.put(customProtocol.getId(),(NioSocketChannel)channelHandlerContext.channel()) ;
//    }
//
//
//
//}
