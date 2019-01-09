package study.nio.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter{
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf)msg;
		byte[] data = new byte[buf.readableBytes()];
		buf.readBytes(data);
		System.out.println("Receive from server " + new String(data));
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		byte[] data = new byte[20];
		 ByteBuf encoded = ctx.alloc().buffer(20);
		data[4] = 12;
		data[8] = 'a';
		encoded.writeBytes(data);
		ctx.writeAndFlush(encoded);
		System.out.println("Send message.");
		System.out.println(ctx.channel().remoteAddress());
	}
	
}
