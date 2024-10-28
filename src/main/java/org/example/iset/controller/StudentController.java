package org.example.iset.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.iset.entity.actors.Student;
import org.example.iset.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;


    // Find all students
    @GetMapping
    public ResponseEntity<?> findAll() {
        HashMap<String, Object> res = new HashMap<>();
        try {
            List<Student> etudiants = studentService.findAll();
            res.put("message", "ok");
            res.put("data", etudiants);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            res.put("message", "error");
            res.put("data", e.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    // Find a student by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            Student student = studentService.findById(id);
            res.put("message", "ok");
            res.put("data", student);
            return ResponseEntity.ok(res);
        } catch (EntityNotFoundException e) {
            res.put("message", "Student not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        } catch (Exception e) {
            res.put("message", "error");
            res.put("data", e.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    // Save or update a student
    @PostMapping
    public ResponseEntity<?> saveStudent(@RequestBody Student student) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            Student savedStudent = studentService.save(student);
            res.put("message", "Student saved successfully");
            res.put("data", savedStudent);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            res.put("message", "error");
            res.put("data", e.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    // Delete a student by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Integer id) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            studentService.delete(id);
            res.put("message", "Student deleted successfully");
            return ResponseEntity.ok(res);
        } catch (EntityNotFoundException e) {
            res.put("message", "Student not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        } catch (Exception e) {
            res.put("message", "error");
            res.put("data", e.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    // Find a student by CIN
    @GetMapping("/cin/{cin}")
    public ResponseEntity<?> findByCin(@PathVariable Integer cin) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            Student student = studentService.findByIdCin(cin);
            res.put("message", "ok");
            res.put("data", student);
            return ResponseEntity.ok(res);
        } catch (EntityNotFoundException e) {
            res.put("message", "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        } catch (Exception e) {
            res.put("message", "error");
            res.put("data", e.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }
}
