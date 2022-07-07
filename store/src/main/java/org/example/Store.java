package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Store {

   private int id;
   private final List<Group> groups = new ArrayList<>();

   public int getId() {
      return id;
   }

   public int getAmountOfArticle(String articleName) throws Exception {
      Article chosenArticle = this.findArticle(articleName);
      if (chosenArticle == null)
         throw new Exception("No article with name \"" + articleName + '"');
      return chosenArticle.getAmount();
   }

   public void decrementArticle(String articleName, int decrement) throws Exception {
      Article chosenArticle = this.findArticle(articleName);
      if (chosenArticle == null)
         throw new Exception("No article with name \"" + articleName + '"');
      int amount = chosenArticle.getAmount();
      if (amount - decrement < 0)
         throw new Exception("Cannot decrement by " + decrement + " because article amount is " + amount);
      chosenArticle.setAmount(amount - decrement);
   }

   public void incrementArticle(String articleName, int increment) throws Exception {
      Article chosenArticle = this.findArticle(articleName);
      if (chosenArticle == null)
         throw new Exception("No article with name \"" + articleName + '"');
      int amount = chosenArticle.getAmount();
      chosenArticle.setAmount(amount + increment);
   }

   public void addGroup(Group group) throws Exception {
      String groupName = group.getName();
      if (this.findGroup(groupName) != null)
         throw new Exception("Cannot add group with name\"" + groupName + "\" as it already exists");
      this.groups.add(group);
   }

   public void addArticleToGroup(Article article, String groupName) throws Exception {
      Group chosenGroup = this.findGroup(groupName);
      if (chosenGroup == null)
         throw new Exception("No group with name \"" + groupName + '"');
      if (this.findArticle(article.getName()) != null)
         throw new Exception("Cannot add article with name\"" + article.getName() + "\" as it already exists");
      chosenGroup.addArticle(article);
   }


   public void setPriceForArticle(String articleName, double price) throws Exception {
      Article chosenArticle = this.findArticle(articleName);
      if (chosenArticle == null)
         throw new Exception("No article with name \"" + articleName + '"');
      chosenArticle.setPrice(price);
   }

   public Article findArticle(String articleName) {
      List<Article> articles = this.getAllArticles();
      return articles.stream()
          .filter(article -> articleName.equals(article.getName()))
          .findAny()
          .orElse(null);
   }

   public Group findGroup(String groupName) {
      return this.groups.stream()
          .filter(group -> groupName.equals(group.getName()))
          .findAny()
          .orElse(null);
   }

   public List<Article> getAllArticles() {
      return this.groups.stream()
          .flatMap(s -> s.getArticles().stream())
          .collect(Collectors.toList());
   }

   public List<Group> getGroups() {
      return groups;
   }
}


