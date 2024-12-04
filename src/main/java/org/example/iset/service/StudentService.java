package org.example.iset.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.iset.entity.actors.Student;
import org.example.iset.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository etudiantRepository;

    public List<Student> findAll() {
        return etudiantRepository.findAll();
    }

    public Student findById(Integer id) {
        return etudiantRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Etudiant Not Found")
        );
    }

    public Student save(Student student) {
        return etudiantRepository.saveAndFlush(student);
    }

    public void delete(Integer id) {
        etudiantRepository.delete(findById(id));
    }

    public Student findByIdCin(Integer cin) {
        return etudiantRepository.findByUser_Cin(cin).orElseThrow(
                () -> new EntityNotFoundException("User Not Found")
        );
    }
}
