package org.example.iset.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.iset.entity.actors.Admin;
import org.example.iset.repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    public Admin findById(Integer id) {
        return adminRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Admin Not Found")
        );
    }

    public Admin save(Admin admin) {
        return adminRepository.saveAndFlush(admin);
    }

    public void delete(Integer id) {
        adminRepository.delete(findById(id));
    }

    public Admin findByIdCin(Integer cin) {
        return adminRepository.findByUser_Cin(cin).orElseThrow(
                () -> new EntityNotFoundException("Admin Not Found")
        );
    }
}
