package netty.test2.client;
 
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

public class TimeClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf m = (ByteBuf) msg;
        try {
            long currentTimeMillis = (m.readUnsignedInt() - 2208988800L) * 1000L;
            System.out.println(new Date(currentTimeMillis));
        } catch(Exception e){
        	e.printStackTrace();
        }finally {
            m.release();
        }
    }
    @Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {	
		System.out.println("client channelReadComplete...");
		ctx.flush();//将缓冲区数据写入SocketChannel
	}

//    @Override
//	public void channelActive(ChannelHandlerContext ctx) throws Exception {
//		// TODO Auto-generated method stub
//    	while (true) {
//            ByteBuf time = ctx.alloc().buffer(4); //为ByteBuf分配四个字节
//            time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
//            ctx.writeAndFlush(time); // (3)
//            Thread.sleep(1000);
//        }
//	}

	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}