package netty.test2.server;
 
import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
 
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

	 @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf m = (ByteBuf) msg;
        try {
            long currentTimeMillis = (m.readUnsignedInt() - 2208988800L) * 1000L;
            System.out.println(new Date(currentTimeMillis));
            
            //向客户端写数据
    		String response = "收到：" + currentTimeMillis;
    		ByteBuf buffer = Unpooled.copiedBuffer(response.getBytes());
    		ctx.write(buffer);//写入缓冲数组
        } finally {
            m.release();
        }
    }
	 
	 @Override
		public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {	
			System.out.println("channelReadComplete...");
			ctx.flush();//将缓冲区数据写入SocketChannel
		}
    //ChannelHandlerContext通道处理上下文
    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws InterruptedException { // (1)

        while (true) {
            ByteBuf time = ctx.alloc().buffer(4); //为ByteBuf分配四个字节
            time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
            ctx.writeAndFlush(time); // (3)
            Thread.sleep(2000);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}