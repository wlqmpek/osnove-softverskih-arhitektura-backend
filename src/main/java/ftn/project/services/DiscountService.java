package ftn.project.services;
import ftn.project.models.Discount;
import ftn.project.models.Seller;
import ftn.project.support.InvalidArticleException;

import java.util.List;

public interface DiscountService {
    Discount findOne(Long id);

    List<Discount> findAll();

    Discount save(Discount discount);

    Discount update(Discount discount);

    void remove(Long id);

    void validateDiscount(Discount discount) throws InvalidArticleException;
}
