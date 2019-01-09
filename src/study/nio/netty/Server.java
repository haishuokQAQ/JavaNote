package study.nio.netty;

import java.nio.ByteOrder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class Server {
	public void init() {
		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();
		try {
			ServerBootstrap bootStrap = new ServerBootstrap();
			bootStrap.group(boss, worker).channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 1024).childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(ByteOrder.LITTLE_ENDIAN, 64 * 1024 * 1024, 4, 4, 0, 0, false));
					ch.pipeline().addLast("receiveHandler", new ReceiveHandler());
				}
			});
			ChannelFuture future = bootStrap.bind(2048);
			future.channel().closeFuture().sync();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			boss.shutdownGracefully();
			worker.shutdownGracefully();
		}
		
	}
	
	public static void main(String[] args) {
		Server server = new Server();
		server.init();
	}
}
