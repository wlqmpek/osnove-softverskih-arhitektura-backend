package ftn.project.services.implementation;

import ftn.project.models.Article;
import ftn.project.models.Seller;
import ftn.project.repositories.SellerRepository;
import ftn.project.services.SellerService;
import javassist.NotFoundException;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
}
