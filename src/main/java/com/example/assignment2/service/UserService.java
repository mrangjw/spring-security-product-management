package com.example.assignment2.service;

import com.example.assignment2.entity.Role;
import com.example.assignment2.entity.User;
import com.example.assignment2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 새 사용자 등록
     */
    public User registerUser(String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("이미 존재하는 이메일입니다: " + email);
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.addRole(Role.ROLE_USER); // 기본 권한은 ROLE_USER

        return userRepository.save(user);
    }

    /**
     * 이메일로 사용자 찾기
     */
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    /**
     * ID로 사용자 찾기
     */
    @Transactional(readOnly = true)
    public User findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    /**
     * 모든 사용자 목록 조회
     */
    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * 사용자를 관리자로 승격
     */
    public User makeAdmin(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("사용자를 찾을 수 없습니다. ID: " + userId);
        }

        User user = userOptional.get();

        // 이미 관리자인지 확인
        if (user.getRoles().contains(Role.ROLE_ADMIN)) {
            throw new RuntimeException("이미 관리자 권한을 가진 사용자입니다: " + user.getEmail());
        }

        // 관리자 권한 추가
        user.addRole(Role.ROLE_ADMIN);

        return userRepository.save(user);
    }

    /**
     * 사용자의 관리자 권한 제거 (필요시 사용)
     */
    public User removeAdminRole(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("사용자를 찾을 수 없습니다. ID: " + userId);
        }

        User user = userOptional.get();

        // 관리자 권한 제거
        user.getRoles().remove(Role.ROLE_ADMIN);

        // 권한이 없으면 기본 사용자 권한 추가
        if (user.getRoles().isEmpty()) {
            user.addRole(Role.ROLE_USER);
        }

        return userRepository.save(user);
    }

    /**
     * 관리자 사용자 수 조회
     */
    @Transactional(readOnly = true)
    public long countAdminUsers() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRoles().contains(Role.ROLE_ADMIN))
                .count();
    }

    /**
     * 일반 사용자 수 조회
     */
    @Transactional(readOnly = true)
    public long countRegularUsers() {
        return userRepository.findAll().stream()
                .filter(user -> !user.getRoles().contains(Role.ROLE_ADMIN))
                .count();
    }

    /**
     * 사용자 삭제 (필요시 사용)
     */
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("사용자를 찾을 수 없습니다. ID: " + userId);
        }
        userRepository.deleteById(userId);
    }

    /**
     * 이메일 중복 확인
     */
    @Transactional(readOnly = true)
    public boolean isEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }
}