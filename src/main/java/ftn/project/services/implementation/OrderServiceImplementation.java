package ftn.project.services.implementation;

import ftn.project.models.Order;
import ftn.project.repositories.OrderRepository;
import ftn.project.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class OrderServiceImplementation implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

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
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order update(Order order) {
        return save(order);
    }

    @Override
    public void remove(Long id) {
        orderRepository.delete(findOne(id));
    }
}
