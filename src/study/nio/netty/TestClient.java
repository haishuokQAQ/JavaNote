package study.nio.netty;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.Socket;

public class TestClient {
	public static void main(String[] args) throws Exception{
		Socket socket = new Socket("127.0.0.1", 2048);
		BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
		byte[] data = new byte[20];
		data[4] = 12;
		data[8] = 'a';
		bos.write(data);
		bos.flush();
		Thread.sleep(1000);
		BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
		byte[] outData = new byte[20];
		int ret = bis.read(outData);
		System.out.println(new String(outData, 0, ret));
		socket.close();
	}
}
