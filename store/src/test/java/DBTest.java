import org.example.Article;
import org.example.Db;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DBTest {

   @Test
   public void shouldCreateArticle() {
      Db db = new Db("shouldCreateArticle");
      db.createArticle(new Article("foo", 1, 1));
      List<Article> result = db.getAllArticles();
      Article expected = new Article(1, "foo", 1, 1);
      Article actual = result.get(0);
      Assertions.assertEquals(expected, actual);
   }

   @Test
   public void shouldUpdateArticle() {
      Db db = new Db("shouldUpdateArticle");
      db.createArticle(new Article("foo", 1, 1));
      List<Article> result = db.getAllArticles();
      Article inserted = result.get(0);
      inserted.setPrice(13);
      db.updateArticle(inserted);
      List<Article> resultAfterUpdate = db.getAllArticles();
      Article updated = resultAfterUpdate.get(0);
      Article expected = new Article(1, "foo", 13, 1);
      Assertions.assertEquals(expected, updated);
   }

   @Test
   public void shouldDeleteArticle() {
      Db db = new Db("shouldDeleteArticle");
      db.createArticle(new Article("foo", 1, 1));
      List<Article> result = db.getAllArticles();
      Article inserted = result.get(0);
      db.deleteArticle(inserted.getId());
      List<Article> resultAfterUpdate = db.getAllArticles();
      int expectedLength = 0;
      int actualLength = resultAfterUpdate.size();
      Assertions.assertEquals(expectedLength, actualLength);
   }
}
