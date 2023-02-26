package ftn.project.services.implementation;

import ftn.project.elasticservices.OrderElasticService;
import ftn.project.models.Article;
import ftn.project.models.ArticleElastic;
import ftn.project.models.Order;
import ftn.project.models.OrderElastic;
import ftn.project.repositories.OrderRepository;
import ftn.project.services.OrderService;
import ftn.project.web.dto.order.OrderSearchParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImplementation implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderElasticService orderElasticService;

    @Override
    public Order findOne(Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if(orderOptional.isEmpty()) {
            throw new NoSuchElementException("Order with id = " + id + " not found!");
        }
        return orderOptional.get();
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order save(Order order) throws IOException {

        Order savedOrder = orderRepository.save(order);

        orderElasticService.
                indexOrder(savedOrder);


        return savedOrder;
    }

    @Override
    public void remove(Long id) {
        orderRepository.delete(findOne(id));
    }

    @Override
    public List<Order> find(OrderSearchParams searchParams) {
        List<OrderElastic> orderElastics = orderElasticService
                .find(searchParams);



        //List<ArticleElastic> articleElastics = articleElasticService.findByDescription(searchParams);

            List<Order> orders = orderRepository
                .findByOrderIdIn(
                        orderElastics
                                .stream()
                                .map(orderElastic -> orderElastic.getOrderId())
                                .collect(Collectors.toList())
                );


        return orders;
    }
}



























