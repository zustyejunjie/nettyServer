package com.netty.client.messagepack;

import com.netty.client.TimeClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by yejj on 2017/7/13 0013.
 */
public class EchoClient {

    public  void connect(int port,String host) throws Exception{
        //配置客户端NIO线程组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,3000)
                    //匿名内部类
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {

                            /**
                             * 自定义的编解码器，使用message框架，netty的codec框架集成支持该框架
                             */
                            ch.pipeline().addLast("msgpack decoder",new MsgpachDecoder());
                            ch.pipeline().addLast("msgpack encoder",new MsgpackEncoder());
                            /**
                             * 自定义处理器
                             */
                            ch.pipeline().addLast(new EchoClientHandler(10));
                        }
                    });
            //发起异步连接操作
            ChannelFuture f = b.connect(host,port).sync();

            //等待客户端链路关闭
            f.channel().closeFuture().sync();
        }finally {
            //优雅退出 是否NIO线程组
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        new TimeClient().connect(port,"127.0.0.1");
    }
}
