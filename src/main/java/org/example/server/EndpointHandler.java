package org.example.server;

import com.sun.net.httpserver.HttpExchange;
import java.util.function.Consumer;

public class EndpointHandler {
   private final String pathPattern;
   private final String httpMethod;
   private final Consumer<HttpExchange> handler;

   public EndpointHandler(String pathPattern, String httpMethod, Consumer<HttpExchange> handler) {
      this.pathPattern = pathPattern;
      this.httpMethod = httpMethod;
      this.handler = handler;
   }

   public boolean isMatch(HttpExchange exchange) {
      String exchangeMethod = exchange.getRequestMethod();
      String exchangePath = exchange.getRequestURI().getPath();
      return exchangeMethod.equals(httpMethod) && exchangePath.matches(pathPattern);
   }

   public void handle(HttpExchange exchange) {
      handler.accept(exchange);
   }
}
