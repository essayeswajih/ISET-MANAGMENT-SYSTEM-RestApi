package org.example.iset.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.iset.entity.actors.Admin;
import org.example.iset.tepository.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    List<Admin> findAll(){
        return adminRepository.findAll();
    }
    Admin findById(Integer id){
        return adminRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException("Admin Not Found")
        );
    }
    Admin save(Admin admin){
        return adminRepository.saveAndFlush(admin);
    }

    void delete(Integer id){
        adminRepository.delete(findById(id));
    }
}
