package com.lc.misc.shareDome.nettydemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author luochao
 * @date 2020-04-17 15:09
 */
public class ReactorMain {
    private static ReactorThread[] mainReactorThread = new ReactorThread[1];
    private static ReactorThread[] subReactorThread = new ReactorThread[4];

    public static void main(String[] args) {
        for (int i = 0; i < subReactorThread.length; i++) {
            subReactorThread[i] = new ReactorThread() {
                @Override
                public void handler(SelectionKey selectionKey) throws IOException {
                    SocketChannel ch = (SocketChannel) selectionKey.channel();
                    ByteBuffer requestBuffer = ByteBuffer.allocate(1024);
                    while (ch.isOpen() && ch.read(requestBuffer) != -1) {
                        if (requestBuffer.position() > 0) break;
                    }
                    if (requestBuffer.position() == 0) return; // 如果没数据了, 则不继续后面的处理
                    requestBuffer.flip();
                    byte[] content = new byte[requestBuffer.limit()];
                    requestBuffer.get(content);
                    System.out.println(Thread.currentThread().getName() + "收到数据,来自：" + ch.getRemoteAddress());
                    System.out.println(new String(content));


                    // 响应结果 200
                    String response = "HTTP/1.1 200 OK\r\n" +
                            "Content-Length: 11\r\n\r\n" +
                            "Hello World";
                    ByteBuffer buffer = ByteBuffer.wrap(response.getBytes());
                    while (buffer.hasRemaining()) {
                        ch.write(buffer);
                    }
                }
            };
        }
        for (int i = 0; i < mainReactorThread.length; i++) {
            mainReactorThread[i] = new ReactorThread() {
                AtomicInteger incr = new AtomicInteger(0);

                @Override
                public void handler(SelectionKey selectionKey) throws Exception {
                    int index = incr.incrementAndGet() % subReactorThread.length;
                    ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel accept = channel.accept();
                    accept.configureBlocking(false);
                    subReactorThread[index].doStart();
                    subReactorThread[index].doRegister(accept).interestOps(SelectionKey.OP_READ);
                }
            };
        }

        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            mainReactorThread[0].doStart();
            SelectionKey selectionKey = mainReactorThread[0].doRegister(serverSocketChannel);
            selectionKey.interestOps(SelectionKey.OP_ACCEPT);
            serverSocketChannel.bind(new InetSocketAddress(8899));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
