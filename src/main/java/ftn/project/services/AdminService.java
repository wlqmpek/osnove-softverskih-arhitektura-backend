package ftn.project.services;

import ftn.project.models.Admin;
import ftn.project.models.Article;

import java.util.List;

public interface AdminService {
    Admin findOne(Long id);

    List<Admin> findAll();

    Admin save(Admin admin);

    Admin update(Admin admin);

    void remove(Long id);
}
