package org.example.iset.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.iset.entity.actors.Professor;
import org.example.iset.service.ProfessorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/professors")
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorService professorService;

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
    @PostMapping
    public ResponseEntity<?> saveProfessor(@RequestBody Professor professor) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            Professor savedProfessor = professorService.save(professor);
            res.put("message", "Professor saved successfully");
            res.put("data", savedProfessor);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            res.put("message", "error");
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
}
