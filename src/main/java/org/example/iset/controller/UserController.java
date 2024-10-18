package org.example.iset.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.iset.entity.actors.User;
import org.example.iset.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
//@CrossOrigin("/api/v1/users")
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        HashMap<String, Object> res = new HashMap<>();
        try {
            res.put("message", "ok");
            res.put("data", userService.findAll());
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
            Optional<User> user = Optional.ofNullable(userService.findById(id));
            if (user.isPresent()) {
                res.put("message", "ok");
                res.put("data", user.get());
                return ResponseEntity.ok(res);
            } else {
                res.put("message", "User not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
            }
        } catch (Exception e) {
            res.put("message", "error");
            res.put("data", e.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            User savedUser = userService.save(user);
            res.put("message", "User saved successfully");
            res.put("data", savedUser);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            res.put("message", "error");
            res.put("data", e.getMessage());
            return ResponseEntity.internalServerError().body(res);
        }
    }

    // Delete a user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            userService.delete(id);
            res.put("message", "User deleted successfully");
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

    // Find a user by CIN
    @GetMapping("/cin/{cin}")
    public ResponseEntity<?> findByCin(@PathVariable Integer cin) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            User user = userService.findByIdCin(cin);
            res.put("message", "ok");
            res.put("data", user);
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
