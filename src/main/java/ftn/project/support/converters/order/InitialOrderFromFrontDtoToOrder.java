package ftn.project.support.converters.order;

import ftn.project.models.ArticleQuantity;
import ftn.project.models.Order;
import ftn.project.support.converters.article_quantity.ArticleQuantityFromFrontDtoToArticleQuantity;
import ftn.project.web.dto.article_quantity.ArticleQuantityFromFrontDto;
import ftn.project.web.dto.order.InitialOrderFromFrontDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Component
public class InitialOrderFromFrontDtoToOrder implements Converter<InitialOrderFromFrontDto, Order> {

    @Autowired
    private ArticleQuantityFromFrontDtoToArticleQuantity articleQuantityFromFrontDtoToArticleQuantity;

    @Override
    public Order convert(InitialOrderFromFrontDto initialOrderFromFrontDto) {
        Order order = new Order();
        Set<ArticleQuantity> articleQuantities = new HashSet<>();
        for(ArticleQuantityFromFrontDto aqffd:initialOrderFromFrontDto.getArticleQuantity()) {
            articleQuantities.add(articleQuantityFromFrontDtoToArticleQuantity.convert(aqffd));
        }
        order.setArticleQuantity(articleQuantities);
        order.setTime(LocalDateTime.now());
        return order;
    }
}
