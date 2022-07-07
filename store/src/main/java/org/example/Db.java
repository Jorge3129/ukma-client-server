package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Db {

   private Connection connection;

   public Db(String dbName) {
      try {
         Class.forName("org.sqlite.JDBC");
         Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbName);
         this.connection = connection;

         Statement st1 = connection.createStatement();
         String dropTable = "DROP TABLE IF EXISTS articles";
         System.out.println(dropTable);
         st1.execute(dropTable);

         Statement st = connection.createStatement();
         String createTable = "CREATE TABLE IF NOT EXISTS articles(" +
             "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
             "name TEXT NOT NULL," +
             "price REAL NOT NULL," +
             "amount INT NOT NULL)";
         st.execute(createTable);
      } catch (ClassNotFoundException | SQLException e) {
         throw new RuntimeException(e);
      }
   }

   public Connection getConnection() {
      return connection;
   }

   public void createArticle(Article article) {
      String sql = "INSERT INTO articles (name, price, amount) VALUES (?,?,?)";
      System.out.println(sql);
      try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
         preparedStatement.setString(1, article.getName());
         preparedStatement.setDouble(2, article.getPrice());
         preparedStatement.setInt(3, article.getAmount());
         preparedStatement.execute();
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }

   public List<Article> getAllArticles() {
      List<Article> articles = new ArrayList<>();
      String sql = "SELECT * FROM articles";
      try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
         ResultSet resultSet = preparedStatement.executeQuery();
         while (resultSet.next()) {
            articles.add(new Article(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getDouble("price"),
                resultSet.getInt("amount")
            ));
         }
         resultSet.close();
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
      return articles;
   }

   public void updateArticle(Article article) {
      String sql = "UPDATE articles SET name = ?, price = ?, amount = ? WHERE id = ?";

      try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
         preparedStatement.setString(1, article.getName());
         preparedStatement.setDouble(2, article.getPrice());
         preparedStatement.setInt(3, article.getAmount());
         preparedStatement.setInt(4, article.getId());
         preparedStatement.execute();
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }

   public void deleteArticle(int id) {
      String sql = "DELETE FROM articles WHERE id = ?";
      try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
         preparedStatement.setInt(4, id);
         preparedStatement.execute();
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }

   public static void main(String[] args) {
      Db db = new Db("articles");
      db.createArticle(new Article("a", 12, 12));
      List<Article> articles = db.getAllArticles();
      for (Article a : articles) {
         System.out.println(a);
      }
      try {
         db.getConnection().close();
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }

   }
}
