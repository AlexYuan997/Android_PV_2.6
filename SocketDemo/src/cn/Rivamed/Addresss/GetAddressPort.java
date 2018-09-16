package cn.Rivamed.Addresss;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.net.ssl.SSLException;

import cn.Rivamed.demo.ReciveUDP;
import cn.Rivamed.demo.SendUDP;
import cn.Rivamed.tcpdemo.TCPClient;
import cn.Rivamed.tcpdemo.TCPServer;

public class GetAddressPort {
	
	public static void main(String[] args) throws Exception {
/*	SendUDP.sendUDP();
	ReciveUDP.reciveUDP();*/
		
		TCPServer.reciveTCP();
		TCPClient.sendTCP();

	}
}
