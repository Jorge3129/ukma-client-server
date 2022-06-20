package org.example;

import java.util.Objects;

public class Article {
    private final String name;
    private double price;
    private int amount;

    public Article(String name, double price, int amount) {
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public String getName() {
        return name;
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
}
