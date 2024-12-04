package org.example.iset.repository;

import org.example.iset.entity.actors.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Optional<Admin> findByUser_Cin(Integer cin);
}
