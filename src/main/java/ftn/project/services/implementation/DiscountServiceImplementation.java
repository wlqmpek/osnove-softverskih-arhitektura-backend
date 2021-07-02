package ftn.project.services.implementation;

import ftn.project.models.Article;
import ftn.project.models.Discount;
import ftn.project.models.Seller;
import ftn.project.repositories.DiscountRepository;
import ftn.project.services.DiscountService;
import ftn.project.support.InvalidArticleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class DiscountServiceImplementation implements DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    @Override
    public Discount findOne(Long id) {
        Optional<Discount> discountOptional = discountRepository.findById(id);
        if(discountOptional.isEmpty()) {
            throw new NoSuchElementException("Discount with id = " + id + " not found!");
        }
        return discountOptional.get();
    }

    @Override
    public List<Discount> findAll() {
        return discountRepository.findAll();
    }

    @Override
    public Discount save(Discount discount) {
        return discountRepository.save(discount);
    }

    @Override
    public Discount update(Discount discount) {
        return save(discount);
    }

    @Override
    public void remove(Long id) {
        discountRepository.delete(findOne(id));
    }

    @Override
    public void validateDiscount(Discount discount) throws InvalidArticleException {
        for(Article article:discount.getArticles()) {
            if(!discount.getSeller().getArticles().contains(article)) {
                throw new InvalidArticleException("Not all articles inside the discount are yours!");
            }
        }
    }
}
