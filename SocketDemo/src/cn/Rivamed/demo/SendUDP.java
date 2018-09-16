package cn.Rivamed.demo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class SendUDP {
	
	
	public static void sendUDP() throws IOException {
		// TODO Auto-generated method stub
		Scanner scanner=new Scanner(System.in);
		
		DatagramSocket datagramSocket=new DatagramSocket();

		
		while(true){
			String message=scanner.nextLine();
			DatagramPacket datagramPacket=new DatagramPacket(message.getBytes(), message.length(), 
			InetAddress.getLocalHost(), 10000);
			datagramSocket.send(datagramPacket);
		}

	}

}
