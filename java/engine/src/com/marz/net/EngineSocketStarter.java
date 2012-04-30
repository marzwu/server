package com.marz.net;

import java.io.IOException;
import java.net.ServerSocket;

import com.marz.net.serverSocket.AsynchronousServerSocket;
import com.marz.net.serverSocket.SynchronousServerSocket;

/**
 * @author Marz
 * 
 */
public class EngineSocketStarter {
	public void start() {
		boolean bSyn = false;
		if (bSyn)
			startSyn();
		else
			startAsyn();
	}

	private void startAsyn() {
		new Thread(new AsynchronousServerSocket(3456)).start();
		System.out.println("server socket started in asynchronous");
	}

	private void startSyn() {
		try {
			ServerSocket ss = new ServerSocket(3456);
			while (true) {
				SynchronousServerSocket ms = new SynchronousServerSocket(
						ss.accept());
				ms.start();
				System.out.println("server socket started in synchronous");
			}
		} catch (IOException e) {
			System.out.println(String.format("can't initial connection",
					e.getMessage()));
			e.printStackTrace();
		}
	}
}
