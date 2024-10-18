package org.example.iset.tepository;

import org.example.iset.entity.actors.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor, Integer> {
    Optional<Professor> findByUser_Cin(Integer integer);

    List<Professor> findAllByCourses_CourseName(String courseName);
}
