package ftn.project.services;

import ftn.project.models.Order;
import ftn.project.web.dto.order.OrderSearchParams;

import java.io.IOException;
import java.util.List;

public interface OrderService {
    Order findOne(Long id);

    List<Order> findAll();

    Order save(Order order) throws IOException;

    void remove(Long id);

    List<Order> find(OrderSearchParams searchParams);
}
