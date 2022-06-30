package org.example.socket;

import java.io.IOException;
import java.net.InetAddress;

public class TCPClient {
    private static final int MAX_THREADS = 5;
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException, InterruptedException {
        InetAddress addr = InetAddress.getByName(null);
        while (true) {
            if (ClientThread.threadCount() < MAX_THREADS)
                new ClientThread(addr, PORT);
            Thread.sleep(100);
        }
    }
}
