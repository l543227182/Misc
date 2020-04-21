package com.lc.misc.shareDome.bio;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.System.in;

/**
 * @author luochao
 * @date 2020-04-10 17:23
 */
public class Client {

    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket();
        socket.setTcpNoDelay(true);
        socket.connect(new InetSocketAddress("127.0.0.1", 8899));
        Thread writeThread = writeMsg(socket.getOutputStream());
        Thread readThread = readMsg(socket.getInputStream());
    }

    public static Thread writeMsg(OutputStream outputStream) {
        Thread thread = new Thread(() -> {
            while (true) {
                Scanner scanner = new Scanner(in);
                String s = scanner.nextLine();
                if (StringUtils.isBlank(s)) {
                    continue;
                }
                try {
                    outputStream.write((s + "\n").getBytes());
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if ("close".equals(s)) {
                    System.exit(1);
                    break;
                }
            }
        });
        thread.start();
        return thread;
    }

    public static Thread readMsg(InputStream inputStream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        Thread thread = new Thread(() -> {
            while (true) {
                String s = null;
                try {
                    s = br.readLine();
                    if (StringUtils.isBlank(s)) {
                        continue;
                    }
                    if ("cutdown".equals(s)) {
                        inputStream.close();
                        System.exit(1);
                        break;
                    }
                    System.out.println(s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        return thread;
    }
}
