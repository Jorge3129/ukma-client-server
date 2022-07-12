package org.example.db;

import org.example.models.Article;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Db {

   private final Connection connection;

   public Db(String dbName) {
      try {
         Class.forName("org.sqlite.JDBC");
         Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbName);
         this.connection = connection;

         Statement st1 = connection.createStatement();
         String dropTable = "DROP TABLE IF EXISTS articles";
         st1.execute(dropTable);

         Statement st = connection.createStatement();
         String createTable = "CREATE TABLE IF NOT EXISTS articles(" +
             "id INTEGER PRIMARY KEY NOT NULL," +
             "name TEXT NOT NULL," +
             "price REAL NOT NULL," +
             "amount INT NOT NULL)";
         st.execute(createTable);
         createArticle(new Article("a", 12, 12));
         createArticle(new Article("b", 12, 12));
         createArticle(new Article("c", 12, 12));
         createArticle(new Article("d", 12, 12));
      } catch (ClassNotFoundException | SQLException e) {
         throw new RuntimeException(e);
      }
   }

   public Connection getConnection() {
      return connection;
   }

   public Article createArticle(Article article) {
      String sql = "INSERT INTO articles (name, price, amount) VALUES (?,?,?)";
      String[] generatedColumns = {"ID"};
      try (PreparedStatement preparedStatement = connection.prepareStatement(sql, generatedColumns)) {
         preparedStatement.setString(1, article.getName());
         preparedStatement.setDouble(2, article.getPrice());
         preparedStatement.setInt(3, article.getAmount());
         preparedStatement.execute();

         ResultSet rs = preparedStatement.getGeneratedKeys();
         int id = rs.getInt(1);
         article.setId(id);
         return article;
      } catch (Exception e) {
         e.printStackTrace();
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

   public Article getOneArticle(int id) {
      String sql = "SELECT * FROM articles WHERE id = ?";
      try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
         preparedStatement.setInt(1, id);
         ResultSet resultSet = preparedStatement.executeQuery();
         if (resultSet.next()) {
            return new Article(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getDouble("price"),
                resultSet.getInt("amount")
            );
         }
         resultSet.close();
      } catch (Exception e) {
         System.out.println(e);
         throw new RuntimeException(e);
      }
      return null;
   }

   public Article updateArticle(int id, Article article) {
      String sql = "UPDATE articles SET name = ?, price = ?, amount = ? WHERE id = ?";
      try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
         preparedStatement.setString(1, article.getName());
         preparedStatement.setDouble(2, article.getPrice());
         preparedStatement.setInt(3, article.getAmount());
         preparedStatement.setInt(4, id);
         preparedStatement.execute();
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
      article.setId(id);
      return article;
   }

   public void deleteArticle(int id) {
      String sql = "DELETE FROM articles WHERE id = ?";
      try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
         preparedStatement.setInt(1, id);
         preparedStatement.execute();
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }
}
