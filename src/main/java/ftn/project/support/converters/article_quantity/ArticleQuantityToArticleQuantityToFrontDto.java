package ftn.project.support.converters.article_quantity;

import ftn.project.models.ArticleQuantity;
import ftn.project.web.dto.article_quantity.ArticleQuantityToFrontDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ArticleQuantityToArticleQuantityToFrontDto implements Converter<ArticleQuantity, ArticleQuantityToFrontDto> {
    @Override
    public ArticleQuantityToFrontDto convert(ArticleQuantity articleQuantity) {
        ArticleQuantityToFrontDto articleQuantityToFrontDto = new ArticleQuantityToFrontDto();
        articleQuantityToFrontDto.setArticleQuantityId(articleQuantity.getArticleQuantityId());
        articleQuantityToFrontDto.setOrderId(articleQuantity.getOrder().getOrderId());
        articleQuantityToFrontDto.setArticleId(articleQuantity.getArticle().getArticleId());
        articleQuantityToFrontDto.setQuantity(articleQuantity.getQuantity());

        return articleQuantityToFrontDto;
    }
}
