package com.lc.misc.shareDome.NIO;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/**
 * @author luochao
 * @date 2020-04-14 15:26
 */
public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(8080)); // 绑定端口

        Selector selector = Selector.open();

        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT, serverSocketChannel);// 将serverSocketChannel注册到selector

        while(true) {
            selector.select();

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (Objects.nonNull(selectionKey)&& iterator.hasNext()) {
                SelectionKey next = iterator.next();

                if (next.isAcceptable()) {

                } else if (next.isReadable()) {

                } else if (next.isWritable()) {

                }
                iterator.remove();
            }
        }
    }
}
