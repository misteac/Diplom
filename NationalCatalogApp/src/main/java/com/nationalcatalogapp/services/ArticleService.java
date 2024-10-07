package com.nationalcatalogapp.services;

import com.nationalcatalogapp.models.Article;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArticleService {
    private List<Article> articles = new ArrayList<>();

    // Метод для добавления артикула
    public void addArticle(Article article) {
        articles.add(article);
        System.out.println("Article added: " + article);
    }

    // Метод для удаления артикула по его ID
    public void deleteArticle(String id) {
        Optional<Article> articleToDelete = articles.stream()
                .filter(article -> article.getId().equals(id))
                .findFirst();
        if (articleToDelete.isPresent()) {
            articles.remove(articleToDelete.get());
            System.out.println("Article deleted: " + articleToDelete.get());
        } else {
            System.out.println("Article with ID " + id + " not found.");
        }
    }

    // Метод для редактирования артикула по его ID
    public void editArticle(String id, String newName, String newDescription, double newPrice) {
        Optional<Article> articleToEdit = articles.stream()
                .filter(article -> article.getId().equals(id))
                .findFirst();
        if (articleToEdit.isPresent()) {
            Article article = articleToEdit.get();
            article.setName(newName);
            article.setDescription(newDescription);
            article.setPrice(newPrice);
            System.out.println("Article updated: " + article);
        } else {
            System.out.println("Article with ID " + id + " not found.");
        }
    }

    // Метод для поиска артикула по его ID
    public Article getArticleById(String id) {
        return articles.stream()
                .filter(article -> article.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Метод для получения всех артикулов
    public List<Article> getAllArticles() {
        return new ArrayList<>(articles);
    }
}
