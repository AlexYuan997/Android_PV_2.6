package cn.rivamed.mode1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPThreadServer {
	
	
	public static void main(String[] args) {
		TCPThreadServer.instanceHMIText("���ʹŵ�˫��λ��⵼��");
	}
	
	
	public static void instanceHMIText(String txt0) {
		ServerSocket server = null;
		try {
			server = new ServerSocket(8014);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		while (true) {
			// ��ȡ��һ���ͻ���,���뿪�����߳�
			Socket socket;
			try {
				socket = server.accept();

				if (socket != null) {
					TCPClinet clinet = new TCPClinet(socket,txt0);
					// ���һ����������һ���߳�
					new Thread(clinet).start();
	
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}