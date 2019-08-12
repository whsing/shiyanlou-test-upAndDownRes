package netty.test3.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class MyChatServerHandler extends SimpleChannelInboundHandler<String>{

    //����������������������ӵ�channel������ߵ�GlobalEventExecutor��д���͵�ʱ�����һ�£�����doc
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * ���������յ��κ�һ���ͻ��˵���Ϣ���ᴥ���������
     * ���ӵĿͻ�����������˷�����Ϣ����ô�����ͻ��˶��յ�����Ϣ���Լ��յ����Լ���+��Ϣ
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();

        channelGroup.forEach(ch -> {
            if(channel !=ch){
                ch.writeAndFlush(channel.remoteAddress() +" ���͵���Ϣ:" +msg+" \n");
            }else{
                ch.writeAndFlush(" ���Լ���"+msg +" \n");
            }
        });
    }


    //��ʾ�������ͻ������ӽ���
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();  //��ʵ�൱��һ��connection

        /**
         * ����channelGroup��writeAndFlush��ʵ���൱��channelGroup�е�ÿ��channel��writeAndFlush
         *
         * ��ȥ�㲥���ٽ��Լ����뵽channelGroup��
         */
        channelGroup.writeAndFlush(" ���������� -" +channel.remoteAddress() +" ����\n");
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush(" ���������� -" +channel.remoteAddress() +" �뿪\n");

        //��֤һ��ÿ�οͻ��˶Ͽ����ӣ������Զ��ش�channelGroup��ɾ������
        System.out.println(channelGroup.size());
        //���ͻ��˺ͷ���˶Ͽ����ӵ�ʱ��������Ƕδ���netty���Զ����ã����Բ���Ҫ��Ϊ��ȥ������
        //channelGroup.remove(channel);
    }

    //���Ӵ��ڻ״̬
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() +" ������");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() +" ������");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}