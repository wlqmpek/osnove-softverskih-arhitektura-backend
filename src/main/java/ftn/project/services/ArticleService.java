package ftn.project.services;

import ftn.project.models.entity.Article;

import java.util.List;

public interface ArticleService {

    Article findOne(Long id);

    List<Article> findAll();

    Article save(Article article);

    Article update(Article article);

    void remove(Long id);

}
