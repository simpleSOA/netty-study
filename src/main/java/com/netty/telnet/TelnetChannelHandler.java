package com.netty.telnet;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetAddress;
import java.util.Date;

/**
 * @author: Administrator
 * Date: 2015/12/8 Time: 15:53
 */
public class TelnetChannelHandler extends SimpleChannelInboundHandler<String> {

    private static final String SEPARATOR = System.getProperty("line.separator");

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.write("Welcome to " + InetAddress.getLocalHost().getHostName() + "!"+SEPARATOR);
        ctx.write("It is " + new Date() + " now."+SEPARATOR);
        ctx.flush();
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
        String rep;
        boolean exit = false;
        if(msg.isEmpty()){
            rep = "Please input something"+SEPARATOR;
        }else if("exit".equalsIgnoreCase(msg)){
            rep ="good bye"+ SEPARATOR;
            exit = true;
        }else{
            rep = "The telnet response is :" +msg +SEPARATOR;
        }
        ChannelFuture future = ctx.writeAndFlush(rep);
        if(exit){
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }
}
