package com.bni.bni.service;

import com.bni.bni.entity.User;
import com.bni.bni.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder encoder;

    public String register(String username, String password) {
        if (repo.existsById(username)) {
            return "User already exists";
        }
        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(encoder.encode(password));
        user.setRole("USER");
        repo.save(user);
        return "Registered successfully";
    }

    public boolean login(String username, String password) {
        Optional<User> user = repo.findByUsername(username);
        return user.isPresent() && encoder.matches(password, user.get().getPasswordHash());
    }
}