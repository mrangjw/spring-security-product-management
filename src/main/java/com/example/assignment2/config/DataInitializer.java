package com.example.assignment2.config;

import com.example.assignment2.entity.Role;
import com.example.assignment2.entity.User;
import com.example.assignment2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // 관리자 계정이 없으면 생성
        if (!userRepository.existsByEmail("admin@example.com")) {
            User admin = new User();
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.addRole(Role.ROLE_USER);
            admin.addRole(Role.ROLE_ADMIN);
            userRepository.save(admin);
            System.out.println("관리자 계정 생성됨: admin@example.com / admin123");
        }

        // 일반 사용자 계정이 없으면 생성
        if (!userRepository.existsByEmail("user@example.com")) {
            User user = new User();
            user.setEmail("user@example.com");
            user.setPassword(passwordEncoder.encode("user123"));
            user.addRole(Role.ROLE_USER);
            userRepository.save(user);
            System.out.println("사용자 계정 생성됨: user@example.com / user123");
        }
    }
}
