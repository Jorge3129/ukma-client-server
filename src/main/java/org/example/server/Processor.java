package org.example.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.OutputStream;

public class Processor {

   private static final ObjectMapper objectMapper = new ObjectMapper();

   public static void process(HttpExchange exchange, Object body, int rCode) {
      boolean isEmpty = body.equals("");
      try {
         byte[] data = objectMapper.writeValueAsBytes(body);
         int length = isEmpty ? -1 : data.length;
         exchange.sendResponseHeaders(rCode, length);
         OutputStream os = exchange.getResponseBody();
         if (!isEmpty) os.write(data);
         os.close();
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }
}
