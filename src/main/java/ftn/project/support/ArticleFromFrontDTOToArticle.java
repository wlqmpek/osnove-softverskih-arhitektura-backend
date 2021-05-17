package ftn.project.support;

import ftn.project.models.dto.ArticleFromFrontDTO;
import ftn.project.models.entity.Article;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ArticleFromFrontDTOToArticle implements Converter<ArticleFromFrontDTO, Article> {

    @Override
    public Article convert(ArticleFromFrontDTO articleFromFrontDTO) {
        return Article.builder().
                name(articleFromFrontDTO.getName()).
                description(articleFromFrontDTO.getDescription()).
                price(articleFromFrontDTO.getPrice()).
                imagePath(articleFromFrontDTO.getImagePath()).
                build();
    }
}
