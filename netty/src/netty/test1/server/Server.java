package netty.test1.server;
 
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
 
/**
 * Netty�����
 * 
 * @author gaoyi
 *
 */
public class Server {
 
								
	private void bind(int port) {
 
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			//����˸��������࣬���Խ��ͷ���˵Ŀ������Ӷ�
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup)
					//ʵ����ServerSocketChannel
					.channel(NioServerSocketChannel.class)
					//����ServerSocketChannel��TCP����
					.option(ChannelOption.SO_BACKLOG, 1024)
					.childHandler(new ChildChannelHandler());
									
			// ChannelFuture�������첽I/O�Ľ��
			ChannelFuture f = bootstrap.bind(port).sync();	
			f.channel().closeFuture().sync();
			
			
			
		} catch (InterruptedException e) {
			System.out.println("����netty�����쳣");
			e.printStackTrace();
		} finally {
			workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
		}
			
	}
	
	private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
 
		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			//handler
			ch.pipeline().addLast(new ServerHandler());
		}
		
	}
 
	public static void main(String[] args) {
		int port = 8888;
		new Server().bind(port);
		
	}
 
}