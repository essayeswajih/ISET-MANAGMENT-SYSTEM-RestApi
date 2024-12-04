package org.example.iset.repository;

import org.example.iset.entity.actors.DepHead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepHeadRepository extends JpaRepository<DepHead,Integer> {

}
