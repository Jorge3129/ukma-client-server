package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Group {
    private final String name;
    private final List<Article> articles = new ArrayList<>();

    public Group(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addArticle(Article article) {
        this.articles.add(article);
    }

    public List<Article> getArticles() {
        return articles;
    }

    public Article findArticle(String articleName) {
        return this.articles.stream()
                .filter(article -> articleName.equals(article.getName()))
                .findAny()
                .orElse(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(name, group.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
