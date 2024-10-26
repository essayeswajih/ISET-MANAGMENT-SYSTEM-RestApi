package org.example.iset.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.iset.entity.actors.Admin;
import org.example.iset.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
//@CrossOrigin("/api/v1/admin")
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final AdminService adminService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        HashMap<String, Object> res = new HashMap<>();
        try {
            res.put("message", "ok");
            res.put("data", adminService.findAll());
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            res.put("message", "error");
            res.put("data", e.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            Optional<Admin> admin = Optional.ofNullable(adminService.findById(id));
            if (admin.isPresent()) {
                res.put("message", "ok");
                res.put("data", admin.get());
                return ResponseEntity.ok(res);
            } else {
                res.put("message", "Admin not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
            }
        } catch (Exception e) {
            res.put("message", "error");
            res.put("data", e.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    @PostMapping
    public ResponseEntity<?> saveAdmin(@RequestBody Admin admin) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            Admin savedAdmin = adminService.save(admin);
            res.put("message", "Admin saved successfully");
            res.put("data", savedAdmin);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            res.put("message", "error");
            res.put("data", e.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    // Delete Admin by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable Integer id) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            adminService.delete(id);
            res.put("message", "Admin deleted successfully");
            return ResponseEntity.ok(res);
        } catch (EntityNotFoundException e) {
            res.put("message", "Admin not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        } catch (Exception e) {
            res.put("message", "error");
            res.put("data", e.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    // Find Admin by CIN
    @GetMapping("/cin/{cin}")
    public ResponseEntity<?> findByCin(@PathVariable Integer cin) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            Admin admin = adminService.findByIdCin(cin);
            res.put("message", "ok");
            res.put("data", admin);
            return ResponseEntity.ok(res);
        } catch (EntityNotFoundException e) {
            res.put("message", "Admin not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        } catch (Exception e) {
            res.put("message", "error");
            res.put("data", e.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }
}
