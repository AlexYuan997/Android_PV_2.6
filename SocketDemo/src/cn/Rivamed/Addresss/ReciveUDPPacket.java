package cn.Rivamed.Addresss;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.Buffer;

import org.w3c.dom.CDATASection;

public class ReciveUDPPacket {
//	Socket socket;
	
	public static void getUDPPacket() throws IOException {
		DatagramSocket datagramSocket=new DatagramSocket(10000);
		
		byte[] b=new byte[1024];
		
		DatagramPacket datagramPacket=new DatagramPacket(b, b.length);
		
		datagramSocket.receive(datagramPacket);
		
		
//		datagramPacket.getPort();
		
		byte[] cs=datagramPacket.getData();
		
		String string=new String(cs, 0, cs.length);
		
		System.out.println(string);
		datagramSocket.close();
	}


}
