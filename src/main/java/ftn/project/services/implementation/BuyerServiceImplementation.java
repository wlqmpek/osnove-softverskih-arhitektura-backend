package ftn.project.services.implementation;

import ftn.project.models.Buyer;
import ftn.project.models.Seller;
import ftn.project.repositories.BuyerRepository;
import ftn.project.services.BuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BuyerServiceImplementation implements BuyerService {

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Buyer findOne(Long id) {
        Optional<Buyer> buyer = buyerRepository.findById(id);
        if(buyer.isEmpty()) {
            throw new NoSuchElementException("Buyer with id = " + id + " not found!");
        }
        return buyer.get();
    }

    @Override
    public Buyer findBuyerByUsername(String username) {
        Optional<Buyer> buyer = buyerRepository.findBuyerByUsername(username);
        if(buyer.isEmpty()) {
            throw new NoSuchElementException("Buyer with username = " + username + " not found!");
        }
        return buyer.get();
    }

    @Override
    public List<Buyer> findAll() {
        return buyerRepository.findAll();
    }

    @Override
    public Buyer save(Buyer buyer) {
        buyer.setPassword(passwordEncoder.encode(buyer.getPassword()));
        return buyerRepository.save(buyer);
    }

    @Override
    public Buyer update(Buyer buyer) {
        return buyerRepository.save(buyer);
    }

    @Override
    public void remove(Long id) {
        buyerRepository.delete(findOne(id));
    }
}
