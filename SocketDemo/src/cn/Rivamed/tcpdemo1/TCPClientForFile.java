package cn.Rivamed.tcpdemo1;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class TCPClientForFile {
	
	public static void sendTCP() throws Exception{
		// TODO Auto-generated method stub
		
		Socket socket=new Socket(InetAddress.getLocalHost().getHostName(), 10001);
		
		
		OutputStream outputStream=socket.getOutputStream();
		
		File file=new File("C:\\dasd.txt");
		
		FileInputStream fileInputStream=new FileInputStream(file);
		
		byte[] b=new byte[1024];
		
		int len=0;
		
		while ((len=fileInputStream.read(b))!=-1) {
			outputStream.write(b, 0, len);
		}
		
		socket.shutdownOutput();
		
		byte bs[]=new byte[1024];
		
		InputStream inputStream=socket.getInputStream();
		
		int length=inputStream.read(bs);
		
		System.out.println(new String(bs, 0, length));
	
		inputStream.close();
		
		outputStream.close();
		
		socket.close();
		

	}
	public static void main(String[] args) throws Exception {
		TCPClientForFile.sendTCP();
	}

}
