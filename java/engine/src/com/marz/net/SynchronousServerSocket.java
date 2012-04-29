package com.marz.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * @author Marz
 * 
 */
public class SynchronousServerSocket implements Runnable {
	private int port;
	private Selector selector;

	private ByteBuffer readBuff = ByteBuffer.allocate(1024);
	private ByteBuffer writeBuff;

	/** signal listener */

	public SynchronousServerSocket(int port) {
		this.port = port;
		try {
			selector = Selector.open();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			ServerSocketChannel ssc = ServerSocketChannel.open();
			ssc.configureBlocking(false);
			ssc.socket().bind(new InetSocketAddress(port));
			ssc.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("server launched");

			while (true) {
				selector.select();
				Iterator<SelectionKey> it = selector.selectedKeys().iterator();
				while (it.hasNext()) {
					SelectionKey key = it.next();
					switch (key.readyOps()) {
					case SelectionKey.OP_ACCEPT:
						onAccept(key);
						break;
					case SelectionKey.OP_CONNECT:
						break;
					case SelectionKey.OP_READ:
						onData(key);
						break;
					case SelectionKey.OP_WRITE:
						break;
					}
					it.remove();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void onAccept(SelectionKey key) {
		System.out.println("new user loggin");
		ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
		try {
			SocketChannel sc = ssc.accept();
			sc.configureBlocking(false);
			sc.register(selector, SelectionKey.OP_READ);
			System.out.println("client connected");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void onData(SelectionKey key) {
		SocketChannel sc = (SocketChannel) key.channel();
		readBuff.clear();
		try {
			sc.read(readBuff);
			readBuff.flip();
			String msg = Charset.forName("UTF-8").decode(readBuff).toString();
			readBuff.clear();
			
			writeBuff = ByteBuffer.wrap(msg.getBytes("UTF-8"));
			sc.write(writeBuff);
			writeBuff.clear();
			
			System.out.println(msg);
			
//			if (msg.equalsIgnoreCase("0")) {
//				writeBuff = ByteBuffer.wrap("login success".getBytes("UTF-8"));
//				sc.write(writeBuff);
//				writeBuff.clear();
//			} else if (msg.equalsIgnoreCase("1")) {
//				writeBuff = ByteBuffer.wrap("logout success".getBytes("UTF-8"));
//				sc.write(writeBuff);
//				writeBuff.clear();
//			}
			System.out.println("process completed");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
