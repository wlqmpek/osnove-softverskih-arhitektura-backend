package ftn.project.services;

import ftn.project.models.Authority;
import ftn.project.models.Buyer;

import java.util.List;

public interface AuthorityService {
    Authority findOne(Long id);

    List<Authority> findAll();

    Authority save(Authority authority);

    Authority update(Authority authority);

    void remove(Long id);
}
