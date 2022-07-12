package org.example.services;

import com.sun.net.httpserver.HttpExchange;
import org.example.utils.Config;
import org.example.jwt.JWT;
import org.example.models.User;
import org.example.server.Processor;
import org.example.utils.BodyParser;

import java.util.Map;

public class AuthService {

   private static final BodyParser<User> bodyParser = new BodyParser<>(User.class);

   public static void processLogin(HttpExchange exchange) {
      User user = bodyParser.getRequestBody(exchange);
      if (user.getLogin().equals(Config.LOGIN) && user.getPassword().equals(Config.PASSWORD)) {
         String jwt = JWT.createJWT(user.getLogin());
         Processor.process(exchange, Map.of("token", jwt), 200);
      } else {
         Processor.process(exchange, "Invalid credentials", 401);
      }
   }
}
