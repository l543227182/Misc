package com.lc.misc.shareDome.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * @author luochao
 * @date 2020-04-14 11:12
 */
public class Client {
    private static volatile SocketChannel socketChannel;
    public static void main(String[] args) throws IOException {
        socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8899));
        while (!socketChannel.finishConnect()) {
            // 没连接上,则一直等待
            Thread.yield();
        }
        writeMsg();
        readMsg();
    }

    public static Thread writeMsg() {
        Thread thread = new Thread(() -> {
            while (true) {
                Scanner scanner = new Scanner(System.in);
                String msg = scanner.nextLine() + "\n";
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                try {
                    socketChannel.write(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        return thread;
    }


    public static Thread readMsg() {
        Thread thread = new Thread(() -> {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while(true) {
                buffer.clear();
                StringBuffer sb = new StringBuffer();
                try {
                    int count = 0;
                    while ((count = socketChannel.read(buffer)) > 0) {
                        sb.append(new String(buffer.array(), 0, count));
                    }
                    if (sb.length() > 0) {
                        System.out.print(sb.toString());
                        if ("close".equals(sb.toString())) {
                            socketChannel.close();
                            socketChannel.socket().close();
                        }
                    }
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        return thread;
    }
}
