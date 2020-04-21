package com.lc.misc.shareDome.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author luochao
 * @date 2020-04-14 15:26
 */
public class Server {
    private static ExecutorService threadPool = Executors.newCachedThreadPool();

    private static Set<SocketChannel> channels = new HashSet();
    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(8899));
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT, serverSocketChannel);// 将serverSocketChannel注册到selector
        threadPool.execute(()->{
            try {
                scannerWriteMsg();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        while (true) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                if (next.isAcceptable()) {
                    ServerSocketChannel serverChannel = (ServerSocketChannel) next.channel();
                    //接受新的连接返回和客户端对等的套接字通道
                    SocketChannel channel = serverChannel.accept();
                    channels.add(channel);
                    channel.configureBlocking(false);
                    channel.register(selector, SelectionKey.OP_READ, channel);
                    System.out.println("新客户来了 : " + channel.getRemoteAddress());
                } else if (next.isReadable()) {
                    readMsg(next);
                } else if (next.isWritable()) {
                    writeMsg(next);
                }
                iterator.remove();
            }
        }
    }

    public static void readMsg(SelectionKey selectionKey) throws IOException {
        SocketChannel channel = (SocketChannel) selectionKey.channel();
        int count = 0;
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        StringBuffer sb = new StringBuffer();
        while ((count = channel.read(buffer)) > 0) {
            sb.append(new String(buffer.array(), 0, count));
        }
        System.out.println("" + sb.toString());

        selectionKey.interestOps(selectionKey.interestOps() | SelectionKey.OP_WRITE);
    }

    public static void writeMsg(SelectionKey selectionKey) throws IOException {
        SocketChannel channel = (SocketChannel) selectionKey.channel();
        channel.write(ByteBuffer.wrap("写一下\n".getBytes()));
        selectionKey.interestOps(SelectionKey.OP_READ);
    }

    public static void scannerWriteMsg() throws IOException {
        while(true) {
            Scanner scanner = new Scanner(System.in);
            String s = scanner.nextLine();
            Iterator<SocketChannel> iterator = channels.iterator();
            while(iterator.hasNext()) {
                SocketChannel next = iterator.next();
                next.write(ByteBuffer.wrap(s.getBytes()));
            }
        }
    }
}
