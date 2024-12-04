package org.example.iset.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.iset.entity.Course;
import org.example.iset.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Course findById(Integer id) {
        return courseRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Course Not Found!")
        );
    }

    public Course findByName(String courseName) {
        return courseRepository.findByCourseName(courseName).orElseThrow(
                () -> new EntityNotFoundException("Course Not Found!")
        );
    }

    public Course save(Course course) {
        return courseRepository.saveAndFlush(course);
    }

    public void delete(Integer id) {
        courseRepository.deleteById(id);
    }
}
