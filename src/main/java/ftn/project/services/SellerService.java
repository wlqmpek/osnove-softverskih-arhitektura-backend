package ftn.project.services;

import ftn.project.models.Article;
import ftn.project.models.Seller;

import java.util.List;
import java.util.Optional;

public interface SellerService {
    Seller findOne(Long id);

    Seller findSellerByUsername(String username);

    List<Seller> findAll();

    Seller save(Seller seller);

    Seller update(Seller seller);

    void remove(Long id);

    double calculateRating(Seller seller);

    void deleteArticle(Article article);
}
