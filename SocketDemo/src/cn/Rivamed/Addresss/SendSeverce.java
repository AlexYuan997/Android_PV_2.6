package cn.Rivamed.Addresss;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class SendSeverce {
	Socket socket;
	
	public static void sendUDPPacket() throws IOException {
		byte [] bs="hello world".getBytes();
		DatagramSocket datagramSocket=new DatagramSocket();
		
		DatagramPacket datagramPacket=new DatagramPacket(bs, bs.length, InetAddress.getLocalHost(), 10000);
		
		datagramSocket.send(datagramPacket);
		
		datagramSocket.close();
		
		
		
	}


}
