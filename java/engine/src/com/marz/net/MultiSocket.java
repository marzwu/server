package com.marz.net;

import java.io.IOException;
import java.net.Socket;

/**
 * @author Marz
 * 
 */
public class MultiSocket extends Thread {
	protected Socket client;

	public MultiSocket(Socket s) {
		client = s;
	}

	public void run() {
		try {
			byte[] r = "hello world".getBytes();
			client.getOutputStream().write(r);
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
