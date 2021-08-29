package ftn.project.web.controllers;

import ftn.project.models.ArticleQuantity;
import ftn.project.services.ArticleQuantityService;
import ftn.project.services.DiscountService;
import ftn.project.services.SellerService;
import ftn.project.support.ImageSave;
import ftn.project.support.LogEvents;
import ftn.project.support.converters.article.ArticleToArticleToFrontDto;
import ftn.project.web.dto.article.ArticleFromFrontDto;
import ftn.project.models.Article;
import ftn.project.services.ArticleService;
import ftn.project.support.converters.article.ArticleFromFrontDTOToArticle;
import ftn.project.web.dto.article.ArticleToFrontDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private ArticleFromFrontDTOToArticle articleFromFrontDTOToArticle;

    @Autowired
    private ArticleToArticleToFrontDto articleToArticleToFrontDto;

    @Autowired
    private DiscountService discountService;

    @Autowired
    private ArticleQuantityService articleQuantityService;

    //READ_ALL
    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<ArticleToFrontDto>> findAll() {
        List<Article> articles = articleService.findAll();
        List<ArticleToFrontDto> articleToFrontDtos = new ArrayList<>();
        for(Article a:articles)
            articleToFrontDtos.add(articleToArticleToFrontDto.convert(a));
        return new ResponseEntity(articleToFrontDtos, HttpStatus.OK);
    }

    //GET_ARTICLE_FROM_ORDER
    //TODO: Limit access to certain buyers and sellers using Principal!
    @GetMapping("/order/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<ArticleToFrontDto>> findAllByOrder(@PathVariable("id") Long id) {
        List<Article> articles = articleService.findAll();
        List<ArticleToFrontDto> articleToFrontDtos = new ArrayList<>();
        for(Article article:articles) {
            for(ArticleQuantity articleQuantity:article.getArticleQuantity()) {
                if(articleQuantity.getOrder().getOrderId().equals(id)) {
                    articleToFrontDtos.add(articleToArticleToFrontDto.convert(article));
                    break;
                }
            }
        }

        return new ResponseEntity<>(articleToFrontDtos, HttpStatus.OK);
    }

    //READ_ALL_BY_SELLER
    @GetMapping("/seller/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<ArticleToFrontDto>> findAllBySeller(@PathVariable("id") Long id) {
        System.out.println("Articl findAll hit! " + id);
        List<Article> allArticles = articleService.findAll();
        List<Article> articles = new ArrayList<>();
        for(Article a:allArticles) {
            if(a.getSeller().getUserId().equals(id)) {
                articles.add(a);
            }
        }
        List<ArticleToFrontDto> articlesToFrontDtos = new ArrayList<>();
        for(Article a:articles)
            articlesToFrontDtos.add(articleToArticleToFrontDto.convert(a));
        return new ResponseEntity(articlesToFrontDtos, HttpStatus.OK);
    }

    //READ_ONE
    @GetMapping(value = "/{id}")
    public ResponseEntity<ArticleToFrontDto> findOne(@PathVariable("id") Long id) {
        Article article = articleService.findOne(id);
        ArticleToFrontDto articleToFrontDto = articleToArticleToFrontDto.convert(article);
        return new ResponseEntity<>(articleToFrontDto, HttpStatus.OK);
    }

    //CREATE
    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasAnyRole('SELLER')")
    public ResponseEntity<ArticleFromFrontDto> create(@Valid @RequestBody ArticleFromFrontDto articleFromFrontDTO, Principal principal) {
        ResponseEntity response = null;
        Article createdArticle = articleFromFrontDTOToArticle.convert(articleFromFrontDTO);
        if(createdArticle != null)
            createdArticle.setSeller(sellerService.findSellerByUsername(principal.getName()));
        createdArticle = articleService.save(createdArticle);
        ArticleToFrontDto articleToFrontDto;
        if(createdArticle != null) {
            articleToFrontDto = articleToArticleToFrontDto.convert(createdArticle);

            response = new ResponseEntity(articleToFrontDto, HttpStatus.OK);
        } else {
            response = new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    //UPDATE treba testirati za principal.
    //TODO: Aj molim te vidi refaktorisi ovu bljuvotinu.
    @PutMapping(value = "/{id}", consumes = "application/json")
    @PreAuthorize("hasAnyRole('SELLER')")
    public ResponseEntity<ArticleToFrontDto> update(@RequestBody @Valid ArticleFromFrontDto articleFromFrontDTO, @PathVariable("id") Long id, Principal principal) {
        ResponseEntity response = null;
        Article articleOld = articleService.findOne(id);
        if(!articleOld.getSeller().getUsername().equals(principal.getName())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Article articleNew = articleFromFrontDTOToArticle.convert(id, articleFromFrontDTO, sellerService.findSellerByUsername(principal.getName()));
        articleNew.setImagePath(articleOld.getImagePath());
        articleNew = articleService.update(articleNew);
        ArticleToFrontDto articleToFrontDto = articleToArticleToFrontDto.convert(articleNew);

        response = (articleNew == null) ?
                new ResponseEntity(null, HttpStatus.BAD_REQUEST) : new ResponseEntity(articleToFrontDto, HttpStatus.OK);

        return response;
    }

    //DELETE
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('SELLER')")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") Long id, Principal p) {
        System.out.println("Delete article hitted! " +id);
        ResponseEntity response = null;
        Article article = articleService.findOne(id);
        System.out.println("Hello " + article);
        if(p.getName().equals(article.getSeller().getUsername())) {
            //Delete from seller, discount and articleQuantity
            sellerService.deleteArticle(article);
            discountService.deleteArticle(article);
            articleQuantityService.deleteArticle(article);
//            articleService.remove(article.getArticleId());
            response = (article == null) ?
                    new ResponseEntity(article, HttpStatus.NOT_FOUND) : new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            response = new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        return response;
    }

    //IMAGE
    @PostMapping(value = "/picture", consumes = "multipart/form-data")
    @PreAuthorize("hasAnyRole('SELLER')")
    public ResponseEntity<Long> addPicture(@RequestParam(name = "image") MultipartFile file, Principal principal) throws IOException {
        Article article = new Article();
        article.setImagePath(ImageSave.saveImage(file));
        article.setSeller(sellerService.findSellerByUsername(principal.getName()));
        article = articleService.save(article);
        return new ResponseEntity<>(article.getArticleId(), HttpStatus.OK);
    }
}
