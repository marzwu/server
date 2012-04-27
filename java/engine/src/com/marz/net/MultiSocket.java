package com.marz.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Marz
 * @see java synchronous connections up limited is about 1400, occur a out of
 *      memory exception
 */
public class MultiSocket extends Thread {
	protected Socket client;
	public static int connCounts;

	public MultiSocket(Socket s) {
		client = s;
	}

	public void run() {
		try {
			connCounts++;

			BufferedReader in = new BufferedReader(new InputStreamReader(client
					.getInputStream()));
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
