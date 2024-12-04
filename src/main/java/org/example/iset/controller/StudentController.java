package org.example.iset.controller;

import com.opencsv.CSVReader;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.iset.entity.actors.Student;
import org.example.iset.entity.actors.User;
import org.example.iset.service.StudentService;
import org.example.iset.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/admin/students")
@RequiredArgsConstructor
public class StudentController {
    private static final Logger log = LoggerFactory.getLogger(StudentController.class);

    private final StudentService studentService;
    private final UserService userService;

    // Find all students
    @GetMapping
    public ResponseEntity<?> findAll() {
        HashMap<String, Object> res = new HashMap<>();
        try {
            List<Student> students = studentService.findAll();
            res.put("message", "ok");
            res.put("data", students);
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
    @PostMapping("/")
    public ResponseEntity<?> saveStudent(@RequestBody Student student) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            if (student.getUser() == null || student.getUser().getCin() == null) {
                res.put("message", "Invalid student data: missing user or CIN");
                return ResponseEntity.badRequest().body(res);
            }

            User user;
            try {
                user = userService.findByIdCin(student.getUser().getCin());
            } catch (EntityNotFoundException e) {
                log.info("User not found, creating new user with CIN: {}", student.getUser().getCin());
                user = userService.save(student.getUser());
            }

            student.setUser(user);
            Student savedStudent = studentService.save(student);
            res.put("message", "Student saved successfully");
            res.put("data", savedStudent);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            log.error("Error while saving student", e);
            res.put("message", "An error occurred");
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

    @PostMapping("/upload")
    public ResponseEntity<String> uploadStudents(@RequestParam("file") MultipartFile file) {
        try {
            List<Student> students = processCSVFile(file);
            // Save all students
            for (Student student : students) {
                studentService.save(student);
            }
            return ResponseEntity.ok("Students uploaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error uploading students: " + e.getMessage());
        }
    }

    // Method to process CSV file
    private List<Student> processCSVFile(MultipartFile file) throws Exception {
        List<Student> students = new java.util.ArrayList<>();
        Reader reader = new InputStreamReader(file.getInputStream());
        try (CSVReader csvReader = new CSVReader(reader)) {
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                if (line.length >= 3) {
                    String username = line[0]; // Username
                    String cin = line[1];      // CIN
                    String email = line[2];     // Email
                    // Optionally, add courses by fetching from courseService

                    // Create user and student
                    User newUser = new User();
                    newUser.setUsername(username);
                    newUser.setCin(Integer.valueOf(String.valueOf(cin)));
                    newUser.setEmail(email);
                    User user = userService.save(newUser);
                    Student student = new Student();
                    student.setUser(user);
                    // Set courses if needed, e.g., student.setCourses(courses);
                    students.add(student);
                }
            }
        }
        return students;
    }
}
