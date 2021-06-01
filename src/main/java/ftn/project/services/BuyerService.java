package ftn.project.services;

import ftn.project.models.Article;
import ftn.project.models.Buyer;

import java.util.List;

public interface BuyerService {
    Buyer findOne(Long id);

    List<Buyer> findAll();

    Buyer save(Buyer buyer);

    Buyer update(Buyer buyer);

    void remove(Long id);
}
