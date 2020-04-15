package com.lc.misc.shareDome.BIO;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author luochao
 * @date 2020-04-10 17:32
 */
public class Server {

    private static ExecutorService threadPool = Executors.newCachedThreadPool();
    private static Set<Socket> socketSet = new HashSet<>();

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8899);

        // 服务器发送指令
        threadPool.execute(() -> {
            try {
                scannerMsg();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        while (true) {
            Socket accept = server.accept();
            OutputStream outputStream = accept.getOutputStream();
            InputStream inputStream = accept.getInputStream();
            threadPool.execute(() -> {
                System.out.println("新成员加入- 线程 " + Thread.currentThread().getId() + " 接待");

                writeMsg(outputStream, inputStream, accept);
            });
            socketSet.add(accept);
        }
    }

    public static void writeMsg(OutputStream outputStream, InputStream inputStream,Socket socket) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            String msg;
            while ((msg = reader.readLine()) != null) {
                if ("close".equals(msg)) {
                    socketSet.remove(socket);
                    socket.close();
                    break;
                }
                System.out.println(Thread.currentThread().getId() + ": " + msg);
                outputStream.write((msg + "\n").getBytes());
            }
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void scannerMsg() throws Exception {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String msg = scanner.nextLine() + "\n";
            if (!socketSet.isEmpty()) {
                Set<Socket> removeSet = new HashSet<>();
                for (Socket item : socketSet) {
                    if (item.isConnected() && !item.isClosed()) {
                        item.getOutputStream().write(msg.getBytes());
                    }
                }
                if (!removeSet.isEmpty())
                    socketSet.removeAll(removeSet);
            }
        }
    }
}
