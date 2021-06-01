package ftn.project.services;

import ftn.project.models.Article;
import ftn.project.models.User;

import java.util.List;

public interface UserService {

    User findOne(Long id);

    User findUserByUsername(String username);

    List<User> findAll();

    User save(User user);

    User update(User user);

    void remove(Long id);
}
