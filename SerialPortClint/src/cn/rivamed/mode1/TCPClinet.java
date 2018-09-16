package cn.rivamed.mode1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;


import cn.rivamed.Utils.UtilsFields;

public class TCPClinet implements Runnable {
	private OutputStream outputStream;
	private InputStream inputStream;
	private String txt0;

	Socket socket;
	// ������кź�sockt
	Map<String, Socket> serialandsocket = new HashMap<String, Socket>();

	public void run() {
		sendCommnd();
	}

	public TCPClinet(Socket socket,String text) {
		this.socket = socket;
		this.txt0=text;
	}

	public void sendCommnd() {
		try {
			byte[] b = TCPClinet.getThree0XFF();
			
			outputStream = socket.getOutputStream();
			// 1.�ȷ�����0XFF
			outputStream.write(b);

			String command = UtilsFields.CONNECT;
			byte[] bs = command.getBytes(UtilsFields.GB2312);
			// 2.����׷�ӵ�����0XFF��������ָ��
			outputStream.write(TCPClinet.arrysCombine(bs, b));

			// 3.��ÿ�η��͸��ͻ���֮ǰ����Ҫ�ȷ�������0XFF


			byte[] len = new byte[1024];
			inputStream = socket.getInputStream();
			inputStream.read(len, 0, len.length);
			String leninfotostring = new String(len, Charset.forName(UtilsFields.GB2312));// ��ȡ��ȡ�ͻ��˶���Ϣ

			if (leninfotostring.contains(UtilsFields.COMOK)) {

				// ���������ݲ��
				String[] info = leninfotostring.split(",");

				if (info[5] != null) {
					// ������кź����кŶ�Ӧ��socket
					serialandsocket.put(info[5], socket);
					String string = String.format("t1.txt=\"%S\"", txt0);
					byte[] byte1 = string.getBytes(UtilsFields.GB2312);
					byte[] bcs = TCPClinet.arrysCombine(byte1, b);
					outputStream.write(b);
					outputStream.write(bcs);

					// �򴮿���Ļ��д�����к�
					string = String.format("t0.txt=\"%S\"", info[5]);
					byte1 = string.getBytes(UtilsFields.GB2312);
					bcs = TCPClinet.arrysCombine(byte1, b);
					outputStream.write(b);
					outputStream.write(bcs);

				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// ��2����ת��Ϊ16�������
	public static String Byte2String(byte[] buf) {
		StringBuilder stringBuilder = new StringBuilder("");
		for (int i = 0; i < buf.length; i++) {
			int v = buf[i] & 0xFF;
			String hv = Integer.toHexString(v);// �����������ݵ��ַ�����ʽ����Ϊ16λ�е��޷�������
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString().toUpperCase();
	}

	public static byte[] getThree0XFF() {
		byte[] b = new byte[3];
		b[0] = (byte) 0XFF;
		b[1] = (byte) 0XFF;
		b[2] = (byte) 0XFF;
		return b;
	}
	
	public static byte[] arrysCombine(byte[] bs,byte[]b){
		byte[] bcs=new byte[bs.length+b.length];
		System.arraycopy(bs, 0, bcs, 0, bs.length);
		System.arraycopy(b, 0, bcs, bs.length,b.length);
		return bcs;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public Map<String, Socket> getSerialandsocket() {
		return serialandsocket;
	}

	public void setSerialandsocket(Map<String, Socket> serialandsocket) {
		this.serialandsocket = serialandsocket;
	}

	public String getTxt0() {
		return txt0;
	}

	public void setTxt0(String txt0) {
		this.txt0 = txt0;
	}

	public OutputStream getOutputStream() {
		return outputStream;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

}