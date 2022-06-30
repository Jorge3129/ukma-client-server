package org.example.processing;

import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class Sender {
   public void sendMessage(PrintWriter out) {
      new Thread(() -> {
         try {
            byte[] message = Encryptor.responseByteQueue.poll(10L, TimeUnit.SECONDS);
            assert message != null;
            out.write(new String(message));
         } catch (InterruptedException e) {
            throw new RuntimeException(e);
         }
      }).start();
   }
}
