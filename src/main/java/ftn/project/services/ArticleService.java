package ftn.project.services;

import ftn.project.models.Article;
import ftn.project.models.Discount;
import ftn.project.web.dto.article.ArticleFromFrontDto;
import ftn.project.web.dto.article.ArticleSearchParams;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ArticleService {

    Article findOne(Long id);

    List<Article> findAll();

    Article save(ArticleFromFrontDto articleFromFrontDto) throws IOException;

    Article update(Article article);

    void remove(Long id);

    void bindDiscountToArticle(Discount discount);

    String saveImage(MultipartFile image, String sellerUsername) throws IOException;

    String savePDF(MultipartFile pdf, String sellerUsername) throws IOException;

    List<Article> find(ArticleSearchParams searchParams);

    //File getResourceFilePath(String path);

    //void indexUploadedArticle(ArticleFromFrontDto article) throws IOException;
}
