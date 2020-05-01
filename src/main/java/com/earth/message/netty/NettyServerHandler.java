package com.earth.message.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import org.jboss.netty.channel.ChannelHandlerContext;

import java.nio.charset.Charset;

public class NettyServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(io.netty.channel.ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("server ctx = " + ctx);


        System.out.println("the client message: " + msg);

    }
}
