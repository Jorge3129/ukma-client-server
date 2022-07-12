package org.example.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

public class BodyParser<T> {

   private final Class<T> tClass;

   public BodyParser(Class<T> tClass) {
      this.tClass = tClass;
   }

   private static final ObjectMapper objectMapper = new ObjectMapper();

   public T getRequestBody(HttpExchange exchange) {
      try {
         return objectMapper.readValue(exchange.getRequestBody(), tClass);
      } catch (Exception e) {
         e.printStackTrace();
      }
      return null;
   }
}
