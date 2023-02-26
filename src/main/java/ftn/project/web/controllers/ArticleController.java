package ftn.project.web.controllers;

import ftn.project.elasticservices.ArticleElasticService;
import ftn.project.models.ArticleQuantity;
import ftn.project.models.Picture;
import ftn.project.services.*;
import ftn.project.support.converters.article.ArticleToArticleToBeIndexedDto;
import ftn.project.support.converters.article.ArticleToArticleToFrontDto;
import ftn.project.web.dto.article.ArticleFromFrontDto;
import ftn.project.models.Article;
import ftn.project.web.dto.article.ArticleSearchParams;
import ftn.project.web.dto.article.ArticleToBeIndexedDto;
import ftn.project.web.dto.article.ArticleToFrontDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleElasticService articleElasticService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private ArticleToArticleToFrontDto articleToArticleToFrontDto;

    @Autowired
    private DiscountService discountService;

    @Autowired
    private ArticleQuantityService articleQuantityService;

    //READ_ALL
//    @GetMapping
//    @PreAuthorize("permitAll()")
//    public ResponseEntity<List<ArticleToFrontDto>> findAll() {
//        List<Article> articles = articleService.findAll();
//        List<ArticleToFrontDto> articleToFrontDtos = new ArrayList<>();
//        for(Article a:articles)
//            articleToFrontDtos.add(articleToArticleToFrontDto.convert(a));
//        return new ResponseEntity(articleToFrontDtos, HttpStatus.OK);
//    }

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

    @GetMapping
    public ResponseEntity<List<ArticleToFrontDto>> find(ArticleSearchParams searchParams) {

        List<Article> articles = articleService
                .find(searchParams);


        List<ArticleToFrontDto> toFrontDtos = articles.stream().map(article -> articleToArticleToFrontDto.convert(article)).collect(Collectors.toList());

        return new ResponseEntity<>(toFrontDtos, HttpStatus.OK);
    }


    //CREATE
    //@PostMapping(consumes = "application/json")
    @PostMapping(consumes = {"multipart/form-data"})
    @PreAuthorize("hasAnyRole('SELLER')")
    public ResponseEntity create(@ModelAttribute ArticleFromFrontDto articleFromFrontDTO, Principal principal) {
        articleFromFrontDTO.setSellerUsername(principal.getName());

        try {
            articleService.save(articleFromFrontDTO);
        } catch (IOException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        //newArticleToBeIndexed.setPdf(articleFromFrontDTO.getPdf());

        return new ResponseEntity(HttpStatus.CREATED);
    }

    //UPDATE treba testirati za principal.
    //TODO: Aj molim te vidi refaktorisi ovu bljuvotinu.
//    @PutMapping(value = "/{id}", consumes = "application/json")
//    @PreAuthorize("hasAnyRole('SELLER')")
//    public ResponseEntity<ArticleToFrontDto> update(@RequestBody @Valid ArticleFromFrontDto articleFromFrontDTO, @PathVariable("id") Long id, Principal principal) {
//        ResponseEntity response = null;
//        Article articleOld = articleService.findOne(id);
//
//        if(!articleOld.getSeller().getUsername().equals(principal.getName())) {
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }
//        Article articleNew = articleFromFrontDTOToArticle.convert(id, articleFromFrontDTO, sellerService.findSellerByUsername(principal.getName()));
//        articleNew.setImagePath(articleOld.getImagePath());
//        articleNew = articleService.update(articleNew);
//        ArticleToFrontDto articleToFrontDto = articleToArticleToFrontDto.convert(articleNew);
//
//        response = (articleNew == null) ?
//                new ResponseEntity(null, HttpStatus.BAD_REQUEST) : new ResponseEntity(articleToFrontDto, HttpStatus.OK);
//
//        return response;
//    }

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
            //discountService.deleteArticle(article);
            //articleQuantityService.deleteArticle(article);
//            articleService.remove(article.getArticleId());
            response = (article == null) ?
                    new ResponseEntity(article, HttpStatus.NOT_FOUND) : new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            response = new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        return response;
    }

    //IMAGE
//    @PostMapping(value = "/picture", consumes = "multipart/form-data")
//    @PreAuthorize("hasAnyRole('SELLER')")
//    public ResponseEntity<Long> addPicture(@RequestParam(name = "picture") MultipartFile file, Principal principal) throws IOException {
//        System.out.println("Add pic hitted");
//        Article article = new Article();
//        article.setImagePath(ImageUtil.saveImage(file));
//        article.setSeller(sellerService.findSellerByUsername(principal.getName()));
//        article = articleService.save(article);
//        return new ResponseEntity<>(article.getArticleId(), HttpStatus.OK);
//    }

    // This endpoind shall be used only for editing article's picture.
    // WLQ 180:7
//    @PutMapping(value = "/picture/{id}", consumes = "multipart/form-data")
//    @PreAuthorize("hasAnyRole('SELLER')")
//    public ResponseEntity<Void> editPicture(@PathVariable(name = "id") Long id, @RequestParam(name = "picture") MultipartFile file, Principal principal) throws IOException {
//        ResponseEntity responseEntity;
//        Article articleOld = articleService.findOne(id);
//        Seller seller = sellerService.findSellerByUsername(principal.getName());
//        if(articleOld.getSeller().getUserId().equals(seller.getUserId())) {
//            // Yes doing only this wont delete previous picture, too bad that i do not care.
//            articleOld.setImagePath(ImageUtil.saveImage(file));
//            articleService.save(articleOld);
//            responseEntity = new ResponseEntity(HttpStatus.OK);
//        } else {
//            responseEntity = new ResponseEntity(HttpStatus.UNAUTHORIZED);
//        }
//        return responseEntity;
//    }


    // This endpoint should be used for retrieving article picture.
    @GetMapping(value = "/picture/{id}")
    public ResponseEntity<Picture> getPicture(@PathVariable(name = "id") Long id, HttpServletRequest request) throws Exception {
        System.out.println("Get picture hitted");
        Article article = articleService.findOne(id);
        Picture picture = new Picture(article.getImageName());
//        Path filePath = Paths.get(prefix + article.getImagePath());
//        Resource res = new UrlResource(filePath.toUri());
//        String mimeType = null;
//        try {
//            mimeType = request.getServletContext().getMimeType(res.getFile().getAbsolutePath());
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }

        return new ResponseEntity<>(picture, HttpStatus.OK);
    }

}
