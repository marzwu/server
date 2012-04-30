package com.marz.net.serverSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * java synchronous connections up limited is about 4825 on winXP, occur a out
 * of memory exception
 * 
 * 1400, java 1.6_fastdebug, eclipse 3.4<br>
 * 4825, 2012-5-1 0:10:16, java 1.7.0_04, eclipse 3.7.2
 * 
 * @author Marz
 */
public class SynchronousServerSocket extends Thread {
	protected Socket client;
	public static int connCounts;

	public SynchronousServerSocket(Socket s) {
		client = s;
	}

	public void run() {
		try {
			connCounts++;

			BufferedReader in = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream());
			// Multiple User but can't parallel
			while (true) {
				String str = in.readLine();
				System.out.println(str);
				System.out.println(connCounts);
				out.println("has receive....");
				out.flush();

				byte[] r = "hello world".getBytes();
				client.getOutputStream().write(r);
			}
			// client.close();
		} catch (IOException e) {
			connCounts--;
			e.printStackTrace();
		}
	}
}
