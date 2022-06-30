package org.example.processing;

import org.example.Packet;
import org.example.PacketBuilder;
import org.example.interfaces.IDecryptor;

import java.util.Arrays;
import java.util.concurrent.*;

public class Decryptor implements IDecryptor {

   public static final BlockingQueue<Packet> packetQueue = new ArrayBlockingQueue<>(10);

   @Override
   public void decrypt() {
      new Thread(() -> {
         try {
            byte[] data = Receiver.byteQueue.poll(10L, TimeUnit.SECONDS);
            System.out.println("Decryptor " + Arrays.toString(data));
            Packet result = new PacketBuilder().decode(data);
            packetQueue.put(result);
         } catch (InterruptedException e) {
            throw new RuntimeException(e);
         }
      }).start();
   }
}
