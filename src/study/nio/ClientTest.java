package study.nio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientTest {
	public static void main(String[] args) throws Exception {
		Socket socket = new Socket();
		socket.connect(new InetSocketAddress(2048));
		socket.getOutputStream().write(new String("test").getBytes());
		socket.getOutputStream().flush();
		byte[] data = new byte[1024];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while(true) {
			int ret = socket.getInputStream().read(data);
			baos.write(data, 0, ret);
			baos.flush();
			if (ret <= 0) {
				break;
			}
		}
		
		socket.close();
		System.out.println(new String(baos.toByteArray()));
	}
}
