package org.example.fakes;

import org.example.processing.Encryptor;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class FakeSender {
   public void sendMessage() {
      new Thread(() -> {
         try {
            byte[] message = Encryptor.responseByteQueue.poll(10L, TimeUnit.SECONDS);
            assert message != null;
            System.out.println("Sender " + Arrays.toString(message));
         } catch (InterruptedException e) {
            throw new RuntimeException(e);
         }
      }).start();
   }
}
