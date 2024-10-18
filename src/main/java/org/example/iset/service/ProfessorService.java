package org.example.iset.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.iset.entity.actors.Professor;
import org.example.iset.tepository.ProfessorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProfessorService {
    private final ProfessorRepository professorRepository;

    public List<Professor> findAll() {
        return professorRepository.findAll();
    }

    public Professor findById(Integer id) {
        return professorRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Professor Not Found")
        );
    }

    public Professor save(Professor professor) {
        return professorRepository.saveAndFlush(professor);
    }

    public void delete(Integer id) {
        professorRepository.delete(findById(id));
    }

    public Professor findByIdCin(Integer cin) {
        return professorRepository.findByUser_Cin(cin).orElseThrow(
                () -> new EntityNotFoundException("User Not Found")
        );
    }
}
