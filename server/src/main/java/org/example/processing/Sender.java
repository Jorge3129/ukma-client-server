package org.example.processing;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Sender {
   public void sendMessage() {
      new Thread(() -> {
         try {
            byte[] message = Encryptor.responseByteQueue.poll(10L, TimeUnit.SECONDS);
            System.out.println("Sender " + Arrays.toString(message));
         } catch (InterruptedException e) {
            throw new RuntimeException(e);
         }
      }).start();
   }
}
