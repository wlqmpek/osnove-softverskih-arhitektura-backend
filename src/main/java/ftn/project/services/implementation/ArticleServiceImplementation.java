package ftn.project.services.implementation;


import ftn.project.models.Article;
import ftn.project.models.Buyer;
import ftn.project.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ftn.project.repositories.ArticleRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ArticleServiceImplementation implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Article findOne(Long id) {
        Optional<Article> article = articleRepository.findById(id);
        if(article.isEmpty()) {
                throw new NoSuchElementException("Article with id = " + id + " not found!");
        }
        return article.get();
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
