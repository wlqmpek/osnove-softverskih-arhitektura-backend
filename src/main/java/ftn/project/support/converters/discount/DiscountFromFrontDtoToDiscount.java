package ftn.project.support.converters.discount;

import ftn.project.models.Article;
import ftn.project.models.Discount;
import ftn.project.services.ArticleService;
import ftn.project.web.dto.discount.DiscountFromFrontDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DiscountFromFrontDtoToDiscount implements Converter<DiscountFromFrontDto, Discount> {

    @Autowired
    private ArticleService articleService;

    @Override
    public Discount convert(DiscountFromFrontDto discountFromFrontDto) {
        Discount discount = new Discount();
        discount.setPercentage(discountFromFrontDto.getPercentage());
        discount.setStartDate(discountFromFrontDto.getStartDate());
        discount.setEndDate(discountFromFrontDto.getEndDate());
        discount.setText(discountFromFrontDto.getText());
        Set<Article> articles = new HashSet<>();
        for(Long l:discountFromFrontDto.getArticles()) {
            articles.add(articleService.findOne(l));
        }
        discount.setArticles(articles);
        return discount;
    }
}
