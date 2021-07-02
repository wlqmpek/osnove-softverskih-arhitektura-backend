package ftn.project.services;

import ftn.project.models.Article;
import ftn.project.models.Discount;

import java.util.List;

public interface ArticleService {

    Article findOne(Long id);

    List<Article> findAll();

    Article save(Article article);

    Article update(Article article);

    void remove(Long id);

    void bindDiscountToArticle(Discount discount);
}
