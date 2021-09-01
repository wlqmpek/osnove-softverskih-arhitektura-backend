package ftn.project.support.converters.order;

import ftn.project.models.ArticleQuantity;
import ftn.project.models.Order;
import ftn.project.support.converters.article_quantity.ArticleQuantityToArticleQuantityToFrontDto;
import ftn.project.support.converters.article.ArticleToArticleToFrontDto;
import ftn.project.web.dto.article_quantity.ArticleQuantityToFrontDto;
import ftn.project.web.dto.order.OrderToFrontDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Component
public class OrderToOrderToFrontDto implements Converter<Order, OrderToFrontDto> {

    @Autowired
    private ArticleToArticleToFrontDto articleToArticleToFrontDto;

    @Autowired
    private ArticleQuantityToArticleQuantityToFrontDto articleQuantityToArticleQuantityToFrontDto;

    private static final String DATE_TIME_FORMAT= "yyyy-MM-dd HH:mm:ss";

    @Override
    public OrderToFrontDto convert(Order order) {
        OrderToFrontDto orderToFrontDto = new OrderToFrontDto();
        orderToFrontDto.setOrderId(order.getOrderId());
        LocalDateTime t1 = order.getTime();
        String t2 = t1.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
        LocalDateTime t3 = LocalDateTime.parse(t2, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
        orderToFrontDto.setTime(t3);
        orderToFrontDto.setDelivered(order.isDelivered());
        orderToFrontDto.setBuyer(order.getBuyer().getUserId());
        orderToFrontDto.setRating(order.getRating());
        orderToFrontDto.setComment(order.getComment());
        orderToFrontDto.setAnonymusComment(order.isAnonymusComment());
        orderToFrontDto.setArchivedComment(order.isArchivedComment());
        ArrayList<ArticleQuantityToFrontDto> articleQuantityToFrontDto = new ArrayList<>();
        for(ArticleQuantity articleQuantity:order.getArticleQuantity()) {
            articleQuantityToFrontDto.add(articleQuantityToArticleQuantityToFrontDto.convert(articleQuantity));
        }
        orderToFrontDto.setArticleQuantity(articleQuantityToFrontDto);
        return orderToFrontDto;
    }
}
