package org.example.iset.tepository;

import org.example.iset.entity.actors.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Integer> {
}
