package org.example.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TcpClient {

   public TcpClient(Socket socket) throws IOException {
      InputStream is = socket.getInputStream();
      OutputStream os = socket.getOutputStream();

      new Thread(() -> {
         while (true) {
            byte[] message = new byte[1024];
            try {
               is.read(message);
            } catch (IOException e) {
               throw new RuntimeException(e);
            }
         }
      }).start();
   }
}
