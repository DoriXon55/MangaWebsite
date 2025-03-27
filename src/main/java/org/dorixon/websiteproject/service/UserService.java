package org.dorixon.websiteproject.service;

import jakarta.transaction.Transactional;
import org.dorixon.websiteproject.model.AppUser;
import org.dorixon.websiteproject.repo.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public AppUser registerNewUser(AppUser user) {
        // Sprawdź czy użytkownik o takiej nazwie już istnieje
        if (appUserRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Użytkownik o takiej nazwie już istnieje");
        }

        // Sprawdź czy email już istnieje
        if (appUserRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Ten adres email jest już zarejestrowany");
        }

        // Szyfrowanie hasła
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Zapisz użytkownika
        return appUserRepository.save(user);
    }

    public AppUser findByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }
}