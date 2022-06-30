package org.example.processing;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Receiver {
   public static final BlockingQueue<byte[]> byteQueue = new ArrayBlockingQueue<>(10);


   public void receiveMessage(byte[] encodedPacket) {
      new Thread(() -> {
         System.out.println("Receiver " + Arrays.toString(encodedPacket));
         try {
            byteQueue.put(encodedPacket);
         } catch (InterruptedException e) {
            throw new RuntimeException(e);
         }
      }).start();
   }


}
