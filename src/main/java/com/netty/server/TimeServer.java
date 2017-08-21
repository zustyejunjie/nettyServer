package com.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * Created by yejj on 2017/6/30 0030.
 */
public class TimeServer {

    /**
     * 创建netty服务 绑定端口
     * @param port
     * @throws Exception
     */
    public void bind(int port) throws Exception{
        //配置服务端的NIO线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .childHandler(new ChildChannelHandler());

            //绑定端口，同步等待成功
            ChannelFuture f =  b.bind(port).sync();
            //等待服务端监听端口关闭
            f.channel().closeFuture().sync();

        }finally {
            //优雅退出，释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel>{

        @Override
        protected void initChannel(SocketChannel arg0) throws Exception {

            /**
             * 增加两个解码器，解决tcp粘包拆包的问题
             *
             * StringDecoder 接收的数据 可以直接使用，而不是需要先解码
             */
            arg0.pipeline().addLast(new LineBasedFrameDecoder(1024));
            arg0.pipeline().addLast(new StringDecoder());

            /**
             * 自定义的数据处理器
             */
            arg0.pipeline().addLast(new TimeServerHandler());
        }
    }



    public static void main(String[] args) throws Exception{
        int port = 8080;
        new TimeServer().bind(port);
    }


}
