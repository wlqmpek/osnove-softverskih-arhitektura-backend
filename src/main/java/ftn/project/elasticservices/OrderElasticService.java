package ftn.project.elasticservices;

import ftn.project.models.ArticleElastic;
import ftn.project.models.Order;
import ftn.project.models.OrderElastic;
import ftn.project.web.dto.article.ArticleSearchParams;
import ftn.project.web.dto.article.ArticleToBeIndexedDto;
import ftn.project.web.dto.order.OrderSearchParams;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface OrderElasticService {

    List<OrderElastic> find(OrderSearchParams orderSearchParams);

    OrderElastic index(OrderElastic orderElastic);
    OrderElastic indexOrder(Order order) throws IOException;


}
