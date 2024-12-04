package org.example.iset.controller;

import lombok.RequiredArgsConstructor;
import org.example.iset.entity.Course;
import org.example.iset.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/v1/admin/courses")
public class CourseController {

    private static final Logger log = LoggerFactory.getLogger(CourseController.class);
    private final CourseService courseService;

    // Create a new course
    @PostMapping
    public ResponseEntity<?> createCourse(@RequestBody Course course) {
        try {
            Course savedCourse = courseService.save(course);
            log.info("Course created successfully: {}", savedCourse);
            return new ResponseEntity<>(savedCourse, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating course: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating course: " + e.getMessage());
        }
    }

    // Get all courses
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        try {
            List<Course> courses = courseService.findAll();
            return new ResponseEntity<>(courses, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error retrieving courses: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    // Get a course by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Integer id) {
        try {
            Course course = courseService.findById(id);
            if (course == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Course not found with ID: " + id);
            }
            return new ResponseEntity<>(course, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error retrieving course with ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving course: " + e.getMessage());
        }
    }

    // Update an existing course
    @PutMapping()
    public ResponseEntity<?> updateCourse(@RequestBody Course course) {
        try {
            Course existingCourse = courseService.findById(course.getId());
            if (existingCourse == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Course not found with ID: " + course.getId());
            }
            Course updatedCourse = courseService.save(course);
            return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating course with ID {}: {}", course.getId(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating course: " + e.getMessage());
        }
    }

    // Delete a course by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Integer id) {
        try {
            Course existingCourse = courseService.findById(id);
            if (existingCourse == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Course not found with ID: " + id);
            }
            courseService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("Course deleted successfully");
        } catch (Exception e) {
            log.error("Error deleting course with ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting course: " + e.getMessage());
        }
    }
}
