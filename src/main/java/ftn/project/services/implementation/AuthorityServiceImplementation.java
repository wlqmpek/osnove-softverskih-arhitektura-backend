package ftn.project.services.implementation;

import ftn.project.models.Article;
import ftn.project.models.Authority;
import ftn.project.repositories.AuthorityRepository;
import ftn.project.services.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AuthorityServiceImplementation implements AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public Authority findOne(Long id) {
        Optional<Authority> authority = authorityRepository.findById(id);
        if(authority.isEmpty()) {
            throw new NoSuchElementException("Authority with id = " + id + " not found!");
        }
        return authority.get();
    }

    @Override
    public List<Authority> findAll() {
        return authorityRepository.findAll();
    }

    @Override
    public Authority save(Authority authority) {
        return authorityRepository.save(authority);
    }

    @Override
    public Authority update(Authority authority) {
        return authorityRepository.save(authority);
    }

    @Override
    public void remove(Long id) {
        authorityRepository.delete(authorityRepository.getOne(id));
    }
}
