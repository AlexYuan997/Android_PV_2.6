package cn.rivamed.mode1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPThreadServer {
	
	
	public static void main(String[] args) {
		TCPThreadServer.instanceHMIText("星型磁电双定位标测导管");
	}
	
	
	public static void instanceHMIText(String txt0) {
		ServerSocket server = null;
		try {
			server = new ServerSocket(8014);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		while (true) {
			// 获取到一个客户端,必须开启新线程
			Socket socket;
			try {
				socket = server.accept();

				if (socket != null) {
					TCPClinet clinet = new TCPClinet(socket,txt0);
					// 获得一个监听开启一个线程
					new Thread(clinet).start();
	
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
