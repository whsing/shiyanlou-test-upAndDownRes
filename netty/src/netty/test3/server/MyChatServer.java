package netty.test3.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import io.netty.bootstrap.*;
import io.netty.channel.*;
import io.netty.channel.nio.*;
import io.netty.channel.socket.nio.*;

public class MyChatServer {

	public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup wokerGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,wokerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new MyChatServerInializer());

            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
//            channelFuture.channel().closeFuture().sync();
            
          //标准输入
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            //利用死循环，不断读取服务端在控制台上的输入内容
            for (;;){
            	channelFuture.channel().writeAndFlush(bufferedReader.readLine() +"\r\n");
            }
        }finally {
            bossGroup.shutdownGracefully();
            wokerGroup.shutdownGracefully();
        }
    }
}
