package ftn.project.support.converters.article;

import ftn.project.models.Seller;
import ftn.project.services.SellerService;
import ftn.project.web.dto.article.ArticleFromFrontDto;
import ftn.project.models.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class ArticleFromFrontDTOToArticle implements Converter<ArticleFromFrontDto, Article> {

    @Autowired
    private SellerService sellerService;

    @Override
    public Article convert(ArticleFromFrontDto articleFromFrontDTO) {
        Article article = new Article();
        article.setName(articleFromFrontDTO.getName());
        article.setDescription(articleFromFrontDTO.getDescription());
        article.setPrice(articleFromFrontDTO.getPrice());
        article.setImagePath(articleFromFrontDTO.getImagePath());
        article.setDiscounts(new HashSet<>());
        return article;
    }

    public Article convert(Long id, ArticleFromFrontDto articleFromFrontDTO, Seller seller) {
        Article article = new Article();
        article.setArticleId(id);
        article.setSeller(seller);
        article.setName(articleFromFrontDTO.getName());
        article.setDescription(articleFromFrontDTO.getDescription());
        article.setPrice(articleFromFrontDTO.getPrice());
        article.setImagePath(articleFromFrontDTO.getImagePath());
        article.setDiscounts(new HashSet<>());
        return article;
    }
}
