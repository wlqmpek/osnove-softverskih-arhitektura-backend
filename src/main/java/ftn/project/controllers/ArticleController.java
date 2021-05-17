package ftn.project.controllers;

import ftn.project.models.dto.ArticleFromFrontDTO;
import ftn.project.models.entity.Article;
import ftn.project.services.ArticleService;
import ftn.project.support.ArticleFromFrontDTOToArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleFromFrontDTOToArticle articleFromFrontDTOToArticle;

    //READ_ALL
    @GetMapping
    public ResponseEntity<List<Article>> findAll() {
        List<Article> articles = articleService.findAll();
        return new ResponseEntity(articles, HttpStatus.OK);
    }

    //READ_ONE
    @GetMapping(value = "/{id}")
    public ResponseEntity<Article> findOne(@PathVariable("id") Long id) {
        Article article = articleService.findOne(id);
        return (article == null) ?
                new ResponseEntity(HttpStatus.NOT_FOUND) : new ResponseEntity<>(article, HttpStatus.OK);
    }

    //CREATE
    @PostMapping(consumes = "application/json")
    public ResponseEntity<Article> create(@Valid @RequestBody ArticleFromFrontDTO articleFromFrontDTO) {
        ResponseEntity response = null;
        Article createdArticle = articleService.save(articleFromFrontDTOToArticle.convert(articleFromFrontDTO));
        response = (createdArticle == null) ?
                new ResponseEntity(createdArticle, HttpStatus.NOT_ACCEPTABLE) : new ResponseEntity<>(createdArticle, HttpStatus.CREATED);

        return response;
    }

    //UPDATE
    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<Article> update(@RequestBody @Valid ArticleFromFrontDTO articleFromFrontDTO, @PathVariable("id") Long id) {
        ResponseEntity response = null;
        Article articleOld = articleService.findOne(id);
        if(articleOld == null) {
            response = new ResponseEntity(articleOld, HttpStatus.NOT_FOUND);
        } else {
            Article articleNew = articleFromFrontDTOToArticle.convert(articleFromFrontDTO);
            articleNew.setArticleId(id);
            articleNew = articleService.update(articleNew);
            response = (articleNew == null) ?
                    new ResponseEntity(articleNew, HttpStatus.NOT_ACCEPTABLE) : new ResponseEntity(articleNew, HttpStatus.OK);
        }

        return response;
    }

    //DELETE
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id) {
        ResponseEntity response = null;
        Article article = articleService.findOne(id);
        if(article != null) articleService.remove(article.getArticleId());
        response = (article == null) ?
                new ResponseEntity(article, HttpStatus.NOT_FOUND) : new ResponseEntity(HttpStatus.OK);

        return response;
    }
}
