package ftn.project.services;

import ftn.project.models.Article;
import ftn.project.models.Order;

import java.util.List;

public interface OrderService {
    Order findOne(Long id);

    List<Order> findAll();

    Order save(Order order);

    Order update(Order order);

    void remove(Long id);
}
