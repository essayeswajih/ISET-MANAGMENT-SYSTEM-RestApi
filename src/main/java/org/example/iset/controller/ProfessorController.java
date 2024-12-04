package org.example.iset.controller;

import com.opencsv.CSVReader;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.iset.entity.actors.Professor;
import org.example.iset.entity.actors.User;
import org.example.iset.service.ProfessorService;
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
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/v1/admin/professors")
public class ProfessorController {
    private static final Logger log = LoggerFactory.getLogger(StudentController.class);
    private final ProfessorService professorService;
    private final UserService userService;

    // Find all professors
    @GetMapping
    public ResponseEntity<?> findAll() {
        HashMap<String, Object> res = new HashMap<>();
        try {
            List<Professor> professors = professorService.findAll();
            res.put("message", "ok");
            res.put("data", professors);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            res.put("message", "error");
            res.put("data", e.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    // Find a professor by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            Professor professor = professorService.findById(id);
            res.put("message", "ok");
            res.put("data", professor);
            return ResponseEntity.ok(res);
        } catch (EntityNotFoundException e) {
            res.put("message", "Professor not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        } catch (Exception e) {
            res.put("message", "error");
            res.put("data", e.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    // Save or update a professor
    @PostMapping("/")
    public ResponseEntity<?> saveProfessor(@RequestBody Professor professor) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            // Check if the professor's user or CIN is missing (optional validation step)
            if (professor.getUser() == null || professor.getUser().getCin() == null) {
                res.put("message", "Invalid professor data: missing user or CIN");
                return ResponseEntity.badRequest().body(res);
            }

            // Find or create the user associated with the professor (similar to the student method)
            User user;
            try {
                user = userService.findByIdCin(professor.getUser().getCin());
            } catch (EntityNotFoundException e) {
                log.info("User not found, creating new user with CIN: {}", professor.getUser().getCin());
                user = userService.save(professor.getUser());
            }

            professor.setUser(user);  // Associate the user with the professor

            // Save the professor to the database
            Professor savedProfessor = professorService.save(professor);

            // Return a success response with the saved professor data
            res.put("message", "Professor saved successfully");
            res.put("data", savedProfessor);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            // Log the error and return an internal server error response
            log.error("Error while saving professor", e);
            res.put("message", "An error occurred");
            res.put("data", e.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }


    // Delete a professor by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProfessor(@PathVariable Integer id) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            professorService.delete(id);
            res.put("message", "Professor deleted successfully");
            return ResponseEntity.ok(res);
        } catch (EntityNotFoundException e) {
            res.put("message", "Professor not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        } catch (Exception e) {
            res.put("message", "error");
            res.put("data", e.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    // Find a professor by CIN
    @GetMapping("/cin/{cin}")
    public ResponseEntity<?> findByCin(@PathVariable Integer cin) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            Professor professor = professorService.findByIdCin(cin);
            res.put("message", "ok");
            res.put("data", professor);
            return ResponseEntity.ok(res);
        } catch (EntityNotFoundException e) {
            res.put("message", "Professor not found");
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
            List<Professor> professors = processCSVFile(file);
            // Save all students
            for (Professor professor : professors) {
                professorService.save(professor);
            }
            return ResponseEntity.ok("Professors uploaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error uploading students: " + e.getMessage());
        }
    }

    // Method to process CSV file
    private List<Professor> processCSVFile(MultipartFile file) throws Exception {
        List<Professor> professors = new java.util.ArrayList<>();
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
                    Professor professor = new Professor();
                    professor.setUser(user);
                    // Set courses if needed, e.g., student.setCourses(courses);
                    professors.add(professor);
                }
            }
        }
        return professors;
    }
}
