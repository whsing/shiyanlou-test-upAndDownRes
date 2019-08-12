package netty.test1.server;
 
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
 
/**
 * Handles a server-side channel.
 * @author yi.gao
 *
 */
public class ServerHandler extends ChannelInboundHandlerAdapter{
 
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
	
		ByteBuf buf = (ByteBuf)msg;
		byte[] bytes = new byte[buf.readableBytes()];
		buf.readBytes(bytes);
		String message = new String(bytes, "UTF-8");
		System.out.println("������յ�����Ϣ�� " + message);
		
		//��ͻ���д����
		String response = "hello client";
		ByteBuf buffer = Unpooled.copiedBuffer(response.getBytes());
		ctx.write(buffer);//д�뻺������
	}
 
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {	
		System.out.println("channelReadComplete...");
		ctx.flush();//������������д��SocketChannel
	}
 
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		
		System.out.println("exceptionCaught...");
	}
 
	
 
 
 
}