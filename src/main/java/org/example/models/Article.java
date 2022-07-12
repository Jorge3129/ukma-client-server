package org.example.models;

import java.util.Objects;

public class Article {

   private Integer id;
   private String name;
   private double price;
   private int amount;

   public Article() {
   }

   public Article(int id, String name, double price, int amount) {
      this.id = id;
      this.name = name;
      this.price = price;
      this.amount = amount;
   }

   public Article(String name, double price, int amount) {
      this.name = name;
      this.price = price;
      this.amount = amount;
   }

   public Integer getId() {
      return id;
   }

   public double getPrice() {
      return price;
   }

   public int getAmount() {
      return amount;
   }

   public String getName() {
      return name;
   }

   public void setPrice(double price) {
      this.price = price;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public void setAmount(int amount) {
      this.amount = amount;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Article article = (Article) o;
      return Double.compare(article.price, price) == 0 && amount == article.amount && name.equals(article.name);
   }

   @Override
   public int hashCode() {
      return Objects.hash(name);
   }

   @Override
   public String toString() {
      return "Article{" +
          "id=" + id +
          ", name='" + name + '\'' +
          ", price=" + price +
          ", amount=" + amount +
          '}';
   }
}
