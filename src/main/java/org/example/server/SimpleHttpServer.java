package org.example.server;

import com.sun.net.httpserver.*;
import org.example.jwt.JWT;
import org.example.utils.Config;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Optional;

public class SimpleHttpServer {

   public static void main(String[] args) throws Exception {
      HttpServer server = HttpServer.create();
      server.bind(new InetSocketAddress(Config.PORT), 0);

      HttpContext context = server.createContext("/", new EchoHandler());
      context.setAuthenticator(new Auth());

      server.setExecutor(null);
      server.start();
      System.out.println("Running");
   }

   static class EchoHandler implements HttpHandler {

      @Override
      public void handle(HttpExchange exchange) {
         System.out.println(exchange.getRequestMethod() + " " + exchange.getRequestURI().getPath());
         Optional<EndpointHandler> handler = Routes.handlers.stream()
             .filter(endpointHandler -> endpointHandler.isMatch(exchange))
             .findFirst();

         if (handler.isPresent()) handler.get().handle(exchange);
         else Processor.process(exchange, "", 404);
      }
   }

   static class Auth extends Authenticator {
      @Override
      public Result authenticate(HttpExchange httpExchange) {
         try {
            String path = httpExchange.getRequestURI().getPath();
            if (path.equals("/login")) return new Success(new HttpPrincipal("c0nst", "realm"));

            String jwt = httpExchange.getRequestHeaders().getFirst("jwt");
            if (jwt == null) throw new Exception("JWT not present");

            String login = JWT.parseJWT(jwt);
            if (login == null || !login.equals(Config.LOGIN)) throw new Exception("Invalid login");

            return new Success(new HttpPrincipal(login, "realm"));
         } catch (Exception e) {
            System.out.println(e.getMessage());
            return new Failure(403);
         }
      }
   }
}
