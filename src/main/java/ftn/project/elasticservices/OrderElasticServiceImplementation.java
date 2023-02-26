package ftn.project.elasticservices;

import ftn.project.elasticrepositories.OrderElasticRepository;
import ftn.project.models.ArticleElastic;
import ftn.project.models.LogicalOperator;
import ftn.project.models.Order;
import ftn.project.models.OrderElastic;
import ftn.project.web.dto.SimpleQueryEs;
import ftn.project.web.dto.order.OrderSearchParams;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderElasticServiceImplementation implements OrderElasticService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Autowired
    private OrderElasticRepository orderElasticRepository;

//    @Override
//    public List<OrderElastic> find(OrderSearchParams orderSearchParams) {
//        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
//
//        if (orderSearchParams.getMinTotalPrice() != null || orderSearchParams.getMaxTotalPrice() != null) {
//
//            Double minPrice = orderSearchParams.getMinTotalPrice() != null ? orderSearchParams.getMinTotalPrice() : 0.0;
//            Double maxPrice = orderSearchParams.getMaxTotalPrice() != null ? orderSearchParams.getMaxTotalPrice() : Double.MAX_VALUE;
//
//            QueryBuilder findByTotalPricePrice = SearchQueryGenerator.createRangeQueryBuilder(
//                    new SimpleQueryEs("totalPrice", minPrice + "-" + maxPrice));
//
//            boolQuery.must(findByTotalPricePrice);
//        }
//
//        if (!orderSearchParams.getComment().isBlank()) {
//            QueryBuilder findByComment = SearchQueryGenerator.createMatchQueryBuilder(
//                    new SimpleQueryEs("comment", orderSearchParams.getComment()));
//
//            if (orderSearchParams.getPriceComment() == LogicalOperator.AND) {
//                boolQuery.must(findByComment);
//
//            } else if (orderSearchParams.getPriceComment() == LogicalOperator.OR) {
//                boolQuery.should(findByComment);
//            }
//        }
//
//
//
//        return searchByBoolQuery(boolQuery)
//                .stream()
//                .map(sh -> sh.getContent())
//                .collect(Collectors.toList());
//
//    }

    @Override
    public List<OrderElastic> find(OrderSearchParams orderSearchParams) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        if (orderSearchParams.getMinTotalPrice() != null || orderSearchParams.getMaxTotalPrice() != null) {
            Double minPrice = orderSearchParams.getMinTotalPrice() != null ? orderSearchParams.getMinTotalPrice() : 0.0;
            Double maxPrice = orderSearchParams.getMaxTotalPrice() != null ? orderSearchParams.getMaxTotalPrice() : Double.MAX_VALUE;

            QueryBuilder findByTotalPrice = SearchQueryGenerator.createRangeQueryBuilder(
                    new SimpleQueryEs("totalPrice", minPrice + "-" + maxPrice));

            boolQuery.should(findByTotalPrice);
        }

        if(!orderSearchParams.getComment().isBlank()) {

            QueryBuilder findByComment = SearchQueryGenerator.createMatchQueryBuilder(
                    new SimpleQueryEs("comment", orderSearchParams.getComment())
            );

            boolQuery.should(findByComment);
        }

        if(!orderSearchParams.getComment().isBlank() && (orderSearchParams.getMinTotalPrice() != null || orderSearchParams.getMaxTotalPrice() != null)) {
            boolQuery.minimumShouldMatch(2);
        } else if(!orderSearchParams.getComment().isBlank() || orderSearchParams.getMinTotalPrice() != null || orderSearchParams.getMaxTotalPrice() != null) {
            boolQuery.minimumShouldMatch(1);
        } else {
            boolQuery.minimumShouldMatch(0);
        }



        List<OrderElastic> orders = searchByBoolQuery(boolQuery)
                .stream()
                .map(sh -> sh.getContent())
                .collect(Collectors.toList());

        return orders;

    }

    private SearchHits<OrderElastic> searchByBoolQuery(BoolQueryBuilder boolQuery) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQuery)
                .build();

        return elasticsearchRestTemplate
                .search(searchQuery, OrderElastic.class, IndexCoordinates.of("orders"));
    }

    @Override
    public OrderElastic index(OrderElastic orderElastic) {
        return orderElasticRepository.save(orderElastic);
    }

    @Override
    public OrderElastic indexOrder(Order order) throws IOException {
        OrderElastic orderElastic = OrderElastic
                .builder()
                .orderId(order.getOrderId())
                .comment(order.getComment())
                .totalPrice(
                        order
                                .getArticleQuantity()
                                .stream()
                                .mapToDouble(articleQuantity ->
                                        articleQuantity.getArticle().getPrice() * articleQuantity.getQuantity())
                                .sum()
                )
                .buyerUsername(order.getBuyer().getUsername())
                .build();

        return index(orderElastic);
    }
}
