package org.example.services;

import com.sun.net.httpserver.HttpExchange;
import org.example.models.Article;
import org.example.db.Db;
import org.example.server.Processor;
import org.example.utils.BodyParser;

import java.util.List;

public class ArticleService {

   private static final Db db = new Db("articles.db");
   private static final BodyParser<Article> bodyParser = new BodyParser<>(Article.class);

   public static void getAllArticles(HttpExchange exchange) {
      List<Article> articles = db.getAllArticles();
      Processor.process(exchange, articles, 200);
   }

   public static void getOneArticle(HttpExchange exchange) {
      int id = getId(exchange);
      Article article = db.getOneArticle(id);
      if (article == null) {
         Processor.process(exchange, "Article " + id + " does not exist", 404);
         return;
      }
      Processor.process(exchange, article, 200);
   }

   public static void createArticle(HttpExchange exchange) {
      Article article = bodyParser.getRequestBody(exchange);
      if (isInvalidBody(exchange, article)) return;
      Article createdArticle = db.createArticle(article);
      Processor.process(exchange, createdArticle, 201);
   }

   public static void updateArticle(HttpExchange exchange) {
      Article article = bodyParser.getRequestBody(exchange);
      int id = getId(exchange);
      if (isInvalidBody(exchange, article)) return;
      if (isNullArticle(exchange, id)) return;
      db.updateArticle(id, article);
      Processor.process(exchange, "", 204);
   }

   public static void deleteArticle(HttpExchange exchange) {
      try {
         int id = getId(exchange);
         if (isNullArticle(exchange, id)) return;
         db.deleteArticle(id);
         Processor.process(exchange, "", 204);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   private static boolean isNullArticle(HttpExchange exchange, int id) {
      Article article = db.getOneArticle(id);
      if (article == null) Processor.process(exchange, "Article " + id + " does not exist", 404);
      return article == null;
   }

   private static boolean isInvalidBody(HttpExchange exchange, Article article) {
      boolean isValid = article.getAmount() >= 0 && article.getPrice() >= 0;
      if (!isValid) Processor.process(exchange, "Invalid request body", 409);
      return !isValid;
   }

   private static int getId(HttpExchange exchange) {
      return Integer.parseInt(exchange.getRequestURI().getPath().replace("/api/good/", ""));
   }
}
