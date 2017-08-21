package com.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;

/**
 * Created by yejj on 2017/6/30 0030.
 */
public class TimeServerHandler extends ChannelHandlerAdapter {


    private int counter;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /**
         * 配置了解码器 就不需要这样做了。
         */
//        ByteBuf buf = (ByteBuf)msg;
//        byte[] req = new byte[buf.readableBytes()];
//        buf.readBytes(req);
//        String body = new String(req,"UTF-8").substring(0,req.length
//                -System.getProperty("line.separator").length());

        String body = (String) msg;
        System.out.println("服务器收到数据：  "+body +"计数器值为 "+ ++counter);

        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body)?new Date(System.currentTimeMillis()).toString():"bad order";
        currentTime = currentTime + System.getProperty("line.separator");
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.writeAndFlush(resp);  //把数据写入待发送的缓冲区中 并且写入到socketChannel中 发送
    }

    /**
     * 使用writeAndFlush  就不需要下面的处理了
     * @param ctx
     * @param cause
     * @throws Exception
     */
//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        ctx.flush(); //把消息发送队列的数据写入socketChannel
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close(); //发生异常
    }
}
