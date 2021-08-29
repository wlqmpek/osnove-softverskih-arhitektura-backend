package ftn.project.services;

import ftn.project.models.Article;
import ftn.project.models.ArticleQuantity;
import ftn.project.models.Order;

import java.util.List;

public interface ArticleQuantityService {
    ArticleQuantity findOne(Long id);

    List<ArticleQuantity> findAll();

    ArticleQuantity save(ArticleQuantity articleQuantity);

    ArticleQuantity update(ArticleQuantity articleQuantity);

    void remove(Long id);

    void bindOrderToArticleQuantity(Order order);

    void deleteArticle(Article article);
}
