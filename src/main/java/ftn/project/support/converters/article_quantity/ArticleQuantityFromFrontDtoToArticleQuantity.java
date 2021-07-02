package ftn.project.support.converters.article_quantity;

import ftn.project.models.Article;
import ftn.project.models.ArticleQuantity;
import ftn.project.services.ArticleService;
import ftn.project.web.dto.article_quantity.ArticleQuantityFromFrontDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ArticleQuantityFromFrontDtoToArticleQuantity implements Converter<ArticleQuantityFromFrontDto, ArticleQuantity> {

    @Autowired
    private ArticleService articleService;

    @Override
    public ArticleQuantity convert(ArticleQuantityFromFrontDto articleQuantityFromFrontDto) {
        ArticleQuantity articleQuantity = new ArticleQuantity();
        Article article = articleService.findOne(articleQuantityFromFrontDto.getArticleId());
        articleQuantity.setArticle(article);
        articleQuantity.setQuantity(articleQuantityFromFrontDto.getQuantity());
        return articleQuantity;
    }
}
