package ftn.project.services;

import ftn.project.models.Article;
import ftn.project.models.Seller;

import java.util.List;

public interface SellerService {
    Seller findOne(Long id);

    List<Seller> findAll();

    Seller save(Seller seller);

    Seller update(Seller seller);

    void remove(Long id);
}
