package org.example.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JWT {
   private static final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

   public static String createJWT(String username) {
      return Jwts.builder()
          .setSubject(username)
          .signWith(KEY)
          .compact();
   }

   public static String parseJWT(String jwt) {
      try {
         return Jwts.parserBuilder().setSigningKey(KEY).build()
             .parseClaimsJws(jwt)
             .getBody()
             .getSubject();
      } catch (Exception e) {
         System.out.println(e.getMessage());
      }
      return null;
   }
}
