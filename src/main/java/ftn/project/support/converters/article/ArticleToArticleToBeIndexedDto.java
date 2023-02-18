package ftn.project.support.converters.article;

import ftn.project.models.Article;
import ftn.project.web.dto.article.ArticleToBeIndexedDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ArticleToArticleToBeIndexedDto implements Converter<Article, ArticleToBeIndexedDto> {
    @Override
    public ArticleToBeIndexedDto convert(Article article) {
        ArticleToBeIndexedDto atbid = new ArticleToBeIndexedDto();
        atbid.setArticleId(article.getArticleId());
        atbid.setName(article.getName());
        atbid.setPrice(article.getPrice());
        atbid.setPdfName(article.getPdfName());
        atbid.setSellerUsername(article.getSeller().getUsername());
        return atbid;
    }
}
