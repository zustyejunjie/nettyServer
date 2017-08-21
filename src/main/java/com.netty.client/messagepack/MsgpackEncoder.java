package com.netty.client.messagepack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * Created by yejj on 2017/7/17 0017.
 */
public class MsgpackEncoder extends MessageToByteEncoder<Object>{

    @Override
    protected void encode(ChannelHandlerContext arg0, Object agr1, ByteBuf arg2) throws Exception {
        MessagePack pack = new MessagePack();
        byte[] raw = pack.write(agr1);
        arg2.writeBytes(raw);
    }
}
