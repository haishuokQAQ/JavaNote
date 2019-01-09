package study.nio;

import java.io.File;
import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SelectorTest {
	private static Selector selector ;
	private static Map<Channel, Handle> handleMap = new HashMap<>();
	
	public static void main(String[] args) throws Exception {
		selector = Selector.open();
		ServerSocketChannel channel = ServerSocketChannel.open();
		channel.configureBlocking(false);
		channel.bind(new InetSocketAddress(2048));
		channel.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("Register Accept ok!");
		while(true) {
			selector.select();
			Set<SelectionKey> selectionKeys = selector.selectedKeys();
			for (SelectionKey key : selectionKeys) {
				if (key.isValid()) {
					handle(key);
					selectionKeys.remove(key);
				}
			}
			
		}
	}
	
	private static void handle(SelectionKey key) throws Exception {
		if (key.isAcceptable()) {
			SelectableChannel channel =  (SelectableChannel) key.channel();
			channel.configureBlocking(false);
			System.out.println("On connect ");
			channel.register(selector, SelectionKey.OP_READ);
			
			handleMap.put(channel, new Handle());
		}
		if (key.isReadable() || key.isWritable()) {
			SelectableChannel socketChannel = ((SelectableChannel) key.channel());
			final Handle handle = handleMap.get(socketChannel);
			if (handle != null) {
				handle.handle(key);
			}
		}
	}
	
	
	private static class Handle{
		private StringBuilder message;
		private boolean writeOk = true;
		private ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		private FileChannel fileChannel;
		private String fileName;
		
		public void handle(SelectionKey key) throws Exception {
			if (key.isReadable()) {
				SocketChannel socketChannel = (SocketChannel) key.channel();
				if (writeOk) {
					message = new StringBuilder();
				}
				while(true) {
					int ret = socketChannel.read(byteBuffer);
					if (ret == 0) {
						break;
					}
					if (ret == -1) {
						socketChannel.close();
						key.cancel();
						return;
					}
					message.append(new String(byteBuffer.array(), 0, ret));
				}
				if (writeOk && invokeMessage(message)) {
					writeOk = false;
					socketChannel.register(selector, SelectionKey.OP_WRITE);
				}
			}
			
			if (key.isWritable()) {
				if (!key.isValid()) {
					return;
				}
				SocketChannel socketChannel = (SocketChannel) key.channel();
				if (fileChannel == null) {
					fileChannel = new FileInputStream(new File(fileName)).getChannel();
				}
				byteBuffer.clear();
				while(true) {
					int ret = fileChannel.read(byteBuffer);
					if (ret <= 0) {
						fileName = null;
						fileChannel.close();
						fileChannel = null;
						writeOk = true;
						socketChannel.close();
						key.cancel();
						break;
					}
					byteBuffer.flip();
					socketChannel.write(byteBuffer);
					byteBuffer.clear();
				}
			}
			
		}
		
		private boolean invokeMessage(StringBuilder builder) {
			String m = builder.toString();
			try {
				File f = new File(m);
				if (!f.exists()) {
					return false;
				}
				else {
					fileName = m;
					return true;
				}
			}catch(Exception e) {
				return false;
			}
		}
	}
}
