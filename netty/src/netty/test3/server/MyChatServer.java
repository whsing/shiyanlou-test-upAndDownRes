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
            
          //��׼����
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            //������ѭ�������϶�ȡ������ڿ���̨�ϵ���������
            for (;;){
            	channelFuture.channel().writeAndFlush(bufferedReader.readLine() +"\r\n");
            }
        }finally {
            bossGroup.shutdownGracefully();
            wokerGroup.shutdownGracefully();
        }
    }
}
