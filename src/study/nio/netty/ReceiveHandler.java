package study.nio.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ReceiveHandler extends ChannelInboundHandlerAdapter{
	private mail mailPro = new mail();
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf)msg;
		byte[] data = new byte[buf.readableBytes()];
		buf.readBytes(data);
		System.out.println(data.length);
		byte[] result = mailPro.parse(data, data.length);
		ctx.writeAndFlush(result);
	}
	
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println(ctx.channel().remoteAddress().toString());
	}
	
}
