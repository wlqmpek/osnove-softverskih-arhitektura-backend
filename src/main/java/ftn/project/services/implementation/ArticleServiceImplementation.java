package ftn.project.services.implementation;


import ftn.project.models.Article;
import ftn.project.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ftn.project.repositories.ArticleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImplementation implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Article findOne(Long id) {
        Article article = null;
        Optional<Article> optionalArticle = articleRepository.findById(id);
        article = (optionalArticle.isPresent())?
                optionalArticle.get() : null;
        return article;
    }

    @Override
    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public List<Article> findByNameIsContaining(String str) {
        return articleRepository.findByNameIsContaining(str);
    }

    @Override
    public Article save(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public Article update(Article article) {
//        remove(article.getArticleId());

        return save(article);
    }

    @Override
    public void remove(Long id) {
        articleRepository.delete(findOne(id));
    }


}
