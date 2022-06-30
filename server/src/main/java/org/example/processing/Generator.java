package org.example.processing;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.CommandType;
import org.example.Message;
import org.example.Packet;
import org.example.StoreCommands;

import java.util.Random;

public class Generator {
   private static final Random RANDOM = new Random();

   public static Packet generatePacket(byte clientId, long packetId) {
      return new Packet(clientId, packetId, generateMessage());
   }

   public static Packet generatePacket(long packetId) {
      return new Packet((byte) RANDOM.nextInt(), packetId, generateMessage());
   }

   public static Message generateMessage() {
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
