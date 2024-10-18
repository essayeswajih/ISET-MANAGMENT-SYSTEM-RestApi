package org.example.iset.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.iset.entity.actors.User;
import org.example.iset.tepository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User Not Found")
        );
    }

    public User save(User user) {
        return userRepository.saveAndFlush(user);
    }

    public void delete(Integer id) {
        userRepository.delete(findById(id));
    }

    public User findByIdCin(Integer cin) {
        return userRepository.findUserByCin(cin).orElseThrow(
                () -> new EntityNotFoundException("User Not Found")
        );
    }
}
