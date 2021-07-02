package ftn.project.services.implementation;

import ftn.project.models.Admin;
import ftn.project.repositories.AdminRepository;

import ftn.project.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AdminServiceImplemenatation implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Admin findOne(Long id) {
        Optional<Admin> adminOptional = adminRepository.findById(id);
        if(adminOptional.isEmpty()) {
            throw new NoSuchElementException("Article with id = " + id + " not found!");
        }
        return adminOptional.get();
    }

    @Override
    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    @Override
    public Admin save(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public Admin update(Admin admin) {
        return save(admin);
    }

    @Override
    public void remove(Long id) {
        adminRepository.delete(findOne(id));
    }
}
