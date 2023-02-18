package ftn.project.support.converters.article;

import ftn.project.models.Article;
import ftn.project.models.Discount;
import ftn.project.web.dto.article.ArticleToFrontDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ArticleToArticleToFrontDto implements Converter<Article, ArticleToFrontDto> {

    @Override
    public ArticleToFrontDto convert(Article article) {
        boolean onSale = false;
        ArticleToFrontDto articleToFrontDto = new ArticleToFrontDto();
        articleToFrontDto.setArticleId(article.getArticleId());
        articleToFrontDto.setName(article.getName());
        articleToFrontDto.setDescription(article.getDescription());
        articleToFrontDto.setPrice(article.getPrice());
        articleToFrontDto.setImageName(article.getImageName());
        articleToFrontDto.setSeller(article.getSeller().getUserId());
        for(Discount d:article.getDiscounts()) {
            if((d.getStartDate().isBefore(LocalDate.now()) || d.getStartDate().isEqual(LocalDate.now())) && (d.getEndDate().isAfter(LocalDate.now()) || d.getEndDate().isEqual(LocalDate.now()))) {
                onSale = true;
                articleToFrontDto.setPrice(article.getPrice() - ((article.getPrice() / 100) * d.getPercentage()));
                break;
            }
        }
        articleToFrontDto.setOnSale(onSale);
        return articleToFrontDto;
    }
}
