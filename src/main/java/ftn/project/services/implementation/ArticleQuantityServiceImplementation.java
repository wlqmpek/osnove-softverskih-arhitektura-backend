package ftn.project.services.implementation;

import ftn.project.models.ArticleQuantity;
import ftn.project.models.Order;
import ftn.project.repositories.ArticleQuantityRepository;
import ftn.project.services.ArticleQuantityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ArticleQuantityServiceImplementation implements ArticleQuantityService {

    @Autowired
    private ArticleQuantityRepository articleQuantityRepository;

    @Override
    public ArticleQuantity findOne(Long id) {
        Optional<ArticleQuantity> articleQuantityOptional = articleQuantityRepository.findById(id);
        if(articleQuantityOptional.isEmpty()) {
            throw new NoSuchElementException("Article with id = " + id + " not found!");
        }
        return articleQuantityOptional.get();
    }

    @Override
    public List<ArticleQuantity> findAll() {
        return articleQuantityRepository.findAll();
    }

    @Override
    public ArticleQuantity save(ArticleQuantity articleQuantity) {
        return articleQuantityRepository.save(articleQuantity);
    }

    @Override
    public ArticleQuantity update(ArticleQuantity articleQuantity) {
        return save(articleQuantity);
    }

    @Override
    public void remove(Long id) {
        articleQuantityRepository.delete(findOne(id));
    }

    public void bindOrderToArticleQuantity(Order order) {
        for(ArticleQuantity articleQuantity:order.getArticleQuantity()) {
            articleQuantity.setOrder(order);
            update(articleQuantity);
        }
    }

}
