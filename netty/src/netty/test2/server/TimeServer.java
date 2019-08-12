package netty.test2.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;

/**
 * Created with IntelliJ IDEA.
 * User: ASUS
 * Date: 14-5-7
 * Time: ����10:10
 * To change this template use File | Settings | File Templates.
 */
public class TimeServer {

    public static void main(String[] args) {
        // EventLoop ����ԭ���� ChannelFactory
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(
                                    new TimeServerHandler()
//                                    ,new WriteTimeoutHandler(10)
                                    //����д�볬ʱ10�빹�����10��ʾ�������10���Ӷ�û������д�ˣ���ô�ͳ�ʱ��
//                                  ,new ReadTimeoutHandler(10)
                            );
                        }
                    }).option(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture f = serverBootstrap.bind(9090).sync();
            System.out.println("Server running...");
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}