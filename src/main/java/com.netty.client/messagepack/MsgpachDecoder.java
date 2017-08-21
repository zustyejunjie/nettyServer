package com.netty.client.messagepack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * Created by yejj on 2017/7/17 0017.
 */
public class MsgpachDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext arg0, ByteBuf arg1, List<Object> arg2) throws Exception {
        final byte[] array;
        final int length = arg1.readableBytes();
        array = new byte[length];
        arg1.getBytes(arg1.readerIndex(),array,0,length);
        MessagePack msgPack= new MessagePack();
        arg2.add(msgPack.read(array));
    }
}
