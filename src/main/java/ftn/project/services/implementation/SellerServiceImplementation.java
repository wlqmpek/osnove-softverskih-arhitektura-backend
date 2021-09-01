package ftn.project.services.implementation;

import ftn.project.models.Article;
import ftn.project.models.ArticleQuantity;
import ftn.project.models.Order;
import ftn.project.models.Seller;
import ftn.project.repositories.SellerRepository;
import ftn.project.services.SellerService;
import javassist.NotFoundException;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SellerServiceImplementation implements SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Seller findOne(Long id) {
        Optional<Seller> seller = sellerRepository.findById(id);
        if(seller.isEmpty()) {
            throw new NoSuchElementException("Seller with id = " + id + " not found!");
        }
        return seller.get();
    }

    @Override
    public Seller findSellerByUsername(String username) {
        Optional<Seller> seller = sellerRepository.findSellerByUsername(username);
        if(seller.isEmpty()) {
            throw new NoSuchElementException("Seller with username = " + username + " not found!");
        }
        return seller.get();
    }

    @Override
    public List<Seller> findAll() {
        return sellerRepository.findAll();
    }

    @Override
    public Seller save(Seller seller) {
        seller.setPassword(passwordEncoder.encode(seller.getPassword()));
        return sellerRepository.save(seller);
    }

    @Override
    public Seller update(Seller seller) {
        return sellerRepository.save(seller);
    }

    @Override
    public void remove(Long id) {
        sellerRepository.delete(findOne(id));
    }

    @Override
    public double calculateRating(Seller seller) {
        double rating = 0;
        Set<Order> uniqueOrders = new HashSet<>();

        for(Article article: seller.getArticles()) {
            for(ArticleQuantity articleQuantity:article.getArticleQuantity()) {
                if(articleQuantity.getOrder().isDelivered()) {
                    System.out.println("Added order to seller " + seller.getUserId() + " order " + articleQuantity.getOrder());
                    uniqueOrders.add(articleQuantity.getOrder());
                }
            }
        }
        for(Order order:uniqueOrders) {
            rating += order.getRating();
        }
        if(uniqueOrders.size() == 0) {
            rating = 0;
        } else {
            rating = rating / uniqueOrders.size();
        }
        return Math.round(rating * 100.0) / 100.0;
    }

    @Override
    public void deleteArticle(Article article) {
        Seller seller = findOne(article.getSeller().getUserId());
        System.out.println("Seller before " + seller.getArticles());
        seller.getArticles().remove(article);
        System.out.println("Seller after " + seller.getArticles());
        update(seller);
    }
}
