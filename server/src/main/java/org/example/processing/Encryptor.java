package org.example.processing;

import org.example.Packet;
import org.example.PacketBuilder;
import org.example.interfaces.IEncryptor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Encryptor implements IEncryptor {
   public static final BlockingQueue<byte[]> responseByteQueue = new ArrayBlockingQueue<>(10);

   @Override
   public void encrypt() {
      new Thread(() -> {
         try {
            Packet packet = Processor.responsePacketQueue.poll(10L, TimeUnit.SECONDS);
            System.out.println("Encryptor " + packet);
            byte[] encodedPacket = new PacketBuilder().encode(packet);
            responseByteQueue.put(encodedPacket);
         } catch (InterruptedException e) {
            throw new RuntimeException(e);
         }
      }).start();
   }
}
