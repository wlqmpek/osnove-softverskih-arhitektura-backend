package ftn.project.web.controllers;

import ftn.project.models.Article;
import ftn.project.models.ArticleQuantity;
import ftn.project.models.Order;
import ftn.project.services.ArticleQuantityService;
import ftn.project.services.BuyerService;
import ftn.project.services.OrderService;
import ftn.project.support.converters.order.InitialOrderFromFrontDtoToOrder;
import ftn.project.support.converters.order.OrderToOrderToFrontDto;
import ftn.project.web.dto.article.ArticleSearchParams;
import ftn.project.web.dto.article.ArticleToFrontDto;
import ftn.project.web.dto.order.*;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    @Autowired
    private ArticleQuantityService articleQuantityService;

    @Autowired
    private OrderToOrderToFrontDto orderToOrderToFrontDto;

    @Autowired
    private InitialOrderFromFrontDtoToOrder initialOrderFromFrontDtoToOrder;

    //READ_ONE
    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderToFrontDto> findOne(@PathVariable("id") Long id) {
        System.out.println("Get order hit!");
        Order order = orderService.findOne(id);
        OrderToFrontDto orderToFrontDto = orderToOrderToFrontDto.convert(order);
        return new ResponseEntity<>(orderToFrontDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<OrderToFrontDto>> find(OrderSearchParams searchParams) {

        List<Order> orders = orderService
                .find(searchParams);


        List<OrderToFrontDto> toFrontDtos = orders.stream()
                .map(order -> orderToOrderToFrontDto.convert(order)).collect(Collectors.toList());

        return new ResponseEntity<>(toFrontDtos, HttpStatus.OK);
    }

    //READ_ALL_FROM_BUYER
//    @GetMapping
//    @PreAuthorize("hasAnyRole('BUYER')")
//    public ResponseEntity<List<OrderToFrontDto>> findAllFromSeller(Principal principal) {
//        List<Order> orders = orderService.findAll();
//        List<OrderToFrontDto> orderToFrontDtos = new ArrayList<>();
//        for(Order order:orders) {
//            if(order.getBuyer().getUsername().equals(principal.getName())) {
//                orderToFrontDtos.add(orderToOrderToFrontDto.convert(order));
//            }
//        }
//
//        return new ResponseEntity<>(orderToFrontDtos, HttpStatus.OK);
//    }

    //READ_ALL_UNDELIVERED_FROM_BUYER
    @GetMapping("/undelivered")
    @PreAuthorize("hasAnyRole('BUYER')")
    public ResponseEntity<List<OrderToFrontDto>> findAllFromBuyerNotDelivered(Principal principal) {
        List<Order> orders = orderService.findAll();
        List<OrderToFrontDto> orderToFrontDtos = new ArrayList<>();
        for(Order order:orders) {
            if(order.getBuyer().getUsername().equals(principal.getName()) && !order.isDelivered()) {
                orderToFrontDtos.add(orderToOrderToFrontDto.convert(order));
            }
        }
        return new ResponseEntity<>(orderToFrontDtos, HttpStatus.OK);
    }

    //READ_ALL_DELIVERED_FROM_BUYER
    @GetMapping("/delivered")
    @PreAuthorize("hasAnyRole('BUYER')")
    public ResponseEntity<List<OrderToFrontDto>> findAllFromBuyerDelivered(Principal principal) {
        List<Order> orders = orderService.findAll();
        List<OrderToFrontDto> orderToFrontDtos = new ArrayList<>();
        for(Order order:orders) {
            if(order.getBuyer().getUsername().equals(principal.getName()) && order.isDelivered()) {
                orderToFrontDtos.add(orderToOrderToFrontDto.convert(order));
            }
        }
        return new ResponseEntity<>(orderToFrontDtos, HttpStatus.OK);
    }

    //READ_ALL_DELIVERED_AND_RATED_FROM_SELLER
    @GetMapping("/delivered/commented/")
    @PreAuthorize("hasAnyRole('SELLER')")
    public ResponseEntity<List<OrderToFrontDto>> findAllFromSellersDeliveredAndRated(Principal principal) {
        List<Order> orders = orderService.findAll();
        List<OrderToFrontDto> orderToFrontDtos = new ArrayList<>();
        for(Order order:orders) {
            for(ArticleQuantity articleQuantity:order.getArticleQuantity()) {
                if(articleQuantity.getArticle().getSeller().getUsername().equals(principal.getName())) {
                    if(order.getComment() != null && !order.getComment().isBlank()) {
                        orderToFrontDtos.add(orderToOrderToFrontDto.convert(order));
                    }
                }
                break;
            }
        }
        return new ResponseEntity<>(orderToFrontDtos, HttpStatus.OK);
    }

    //INITIAL_CREATE
    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasAnyRole('BUYER')")
    public ResponseEntity<OrderToFrontDto> initialCreation(@Valid @RequestBody InitialOrderFromFrontDto initialOrderFromFrontDto, Principal principal) throws IOException {
        System.out.println("Creating initial order " + initialOrderFromFrontDto);
        System.out.println();
        ResponseEntity response = null;
        Order createdInitialOrder = initialOrderFromFrontDtoToOrder.convert(initialOrderFromFrontDto);
        if(createdInitialOrder != null) {
            createdInitialOrder.setBuyer(buyerService.findBuyerByUsername(principal.getName()));
        }
        createdInitialOrder = orderService.save(createdInitialOrder);
        OrderToFrontDto orderToFrontDto;
        if(createdInitialOrder != null) {
            //Bindujemo na obe strane.
            articleQuantityService.bindOrderToArticleQuantity(createdInitialOrder);
            orderToFrontDto = orderToOrderToFrontDto.convert(createdInitialOrder);
            response = new ResponseEntity(orderToFrontDto, HttpStatus.OK);
        } else {
            response = new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    //MARK_DELIVERED
    @PutMapping(value = "/delivered/{id}", consumes = "application/json")
    @PreAuthorize("hasAnyRole('BUYER')")
    public ResponseEntity<OrderToFrontDto> setDeliveredOrderFromFront(@PathVariable("id") Long id, Principal principal) throws IOException {
        ResponseEntity response = null;
        Order order = orderService.findOne(id);
        if(order.getBuyer().getUsername().equals(principal.getName())) {
            order.setDelivered(true);
            orderService.save(order);
            OrderToFrontDto orderToFrontDto = orderToOrderToFrontDto.convert(order);
            response = new ResponseEntity(orderToFrontDto, HttpStatus.OK);
        } else {
            response = new ResponseEntity(null, HttpStatus.FORBIDDEN);
        }
        return response;
    }

    //MARK_ARCHIVED_BY_SELLER
    @PutMapping(value = "/archived_comment/{id}", consumes = "application/json")
    @PreAuthorize("hasAnyRole('SELLER')")
    public ResponseEntity<OrderToFrontDto> setArchivedCommentFromFront(@PathVariable("id") Long id, @RequestBody ArchivedCommentOrderFromFrontDto archivedCommentOrderFromFrontDto, Principal principal) throws IOException {
        ResponseEntity response = null;
        Order order = orderService.findOne(id);

        for(ArticleQuantity aq:order.getArticleQuantity()) {
            if(aq.getArticle().getSeller().getUsername().equals(principal.getName())) {
                order.setArchivedComment(archivedCommentOrderFromFrontDto.isArchivedComment());
                orderService.save(order);
                OrderToFrontDto orderToFrontDto = orderToOrderToFrontDto.convert(order);
                response = new ResponseEntity(orderToFrontDto, HttpStatus.OK);
                break;
            } else {
                response = new ResponseEntity(null, HttpStatus.FORBIDDEN);
                break;
            }
        }
        return response;
    }

    //ORDER_FEEDBACK
    @PutMapping(value = "/buyer_feedback/{id}", consumes = "application/json")
    @PreAuthorize("hasAnyRole('BUYER')")
    public ResponseEntity<OrderToFrontDto> buyerFeedbackFromFront(@PathVariable("id") Long id, @Valid @RequestBody BuyerFeedbackOrderFromFront buyerFeedbackOrderFromFront, Principal principal) throws IOException {
        System.out.println("Buyer Feedback Hitted!");
        System.out.println("Buyer feedback " + buyerFeedbackOrderFromFront);
        ResponseEntity response = null;
        Order order = orderService.findOne(id);
        if (order.getBuyer().getUsername().equals(principal.getName())) {
            order.setComment(buyerFeedbackOrderFromFront.getComment());
            order.setRating(buyerFeedbackOrderFromFront.getRating());
            order.setAnonymusComment(buyerFeedbackOrderFromFront.isAnonymusComment());
            OrderToFrontDto orderToFrontDto = orderToOrderToFrontDto.convert(order);
            orderService.save(order);
            response = new ResponseEntity(orderToFrontDto, HttpStatus.OK);
        } else {
            response = new ResponseEntity(null, HttpStatus.FORBIDDEN);
        }

        return response;
    }


}
