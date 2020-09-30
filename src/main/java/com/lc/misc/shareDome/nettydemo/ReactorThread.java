package com.lc.misc.shareDome.nettydemo;

import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author luochao
 * @date 2020-04-15 17:41
 */
public abstract class ReactorThread extends Thread {
    private LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue();
    protected Selector selector;
    private volatile boolean isRunning = false;

    public ReactorThread() {
        try {
            selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (isRunning) {
            Runnable task = null;
            while ((task = queue.poll()) != null) {
                task.run();
            }
            try {
                int select = selector.select(1000);
                if (select > 0) {
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey next = iterator.next();
                        handler(next);
                        iterator.remove();
                    }
                }
                selector.selectNow();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void doStart() {
        if (!isRunning) {
            this.isRunning = true;
            this.start();
        }
    }

    public SelectionKey doRegister(SelectableChannel selectableChannel) throws ExecutionException, InterruptedException {
        FutureTask<SelectionKey> futureTask = new FutureTask<>(() -> selectableChannel.register(selector, 0, selectableChannel));
        queue.add(futureTask);
        SelectionKey selectionKey = futureTask.get();
        return selectionKey;
    }

    public abstract void handler(SelectionKey selectionKey) throws Exception;
}
