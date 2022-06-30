package org.example.processing;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.*;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Receiver {
   private static final Random RANDOM = new Random();
   public static final BlockingQueue<byte[]> byteQueue = new ArrayBlockingQueue<>(10);

   public void receiveMessage() {
      receiveMessage(RANDOM.nextLong());
   }

   public void receiveMessage(long packetId) {
      new Thread(() -> {
         Packet packet = generatePacket(packetId);
         byte[] encodedPacket = new PacketBuilder().encode(packet);
         System.out.println("Receiver " + Arrays.toString(encodedPacket));
         try {
            byteQueue.put(encodedPacket);
         } catch (InterruptedException e) {
            throw new RuntimeException(e);
         }
      }).start();
   }

   private Packet generatePacket(long packetId) {
      return new Packet((byte) RANDOM.nextInt(), packetId, generateMessage());
   }

   private Message generateMessage() {
      try {
         CommandType type = CommandType.CREATE_ARTICLE;
         int userId = RANDOM.nextInt();
         byte[] body = new ObjectMapper().writeValueAsBytes(
             new StoreCommands.CreateArticleBody(
                 "Orange", 12, 2, "Food"
             ));
         return new Message(type, userId, body);
      } catch (Exception e) {
         throw new RuntimeException(e);
      }

   }
}
