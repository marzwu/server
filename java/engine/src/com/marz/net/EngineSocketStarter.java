package com.marz.net;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author Marz
 *
 */
public class EngineSocketStarter {
	public void start() {
		try {
			ServerSocket ss = new ServerSocket(3456);
			while(true)
			{
				MultiSocket ms = new MultiSocket(ss.accept());
				ms.start();
			}
		} catch (IOException e) {
			System.out.println(String.format("can't initial connection", e.getMessage()));
			e.printStackTrace();
		}
	}
}
