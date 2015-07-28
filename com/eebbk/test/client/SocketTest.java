package com.eebbk.test.client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import junit.framework.TestCase;

public class SocketTest extends TestCase{

	public static void main(String[] args) {
		try {
			Socket socket = new Socket("172.28.10.38", 1883);
			System.out.println("socket连接成功...");
			OutputStream os = socket.getOutputStream();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
