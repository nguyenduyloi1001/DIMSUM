package com.example.DIMSUM.config;

import com.example.DIMSUM.model.User;
import com.example.DIMSUM.repository.UserRepository;
import com.example.DIMSUM.enums.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitConfig {
    private final PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            // Kiểm tra nếu CHƯA có admin thì mới tạo
            if (userRepository.findByUsername("admin").isEmpty()) {  // ✅ Sửa ở đây
                User user = User.builder()
                        .username("admin")
                        .fullName("Administrator")
                        .password(passwordEncoder.encode("admin"))
                        .email("admin@example.com")
                        .phone("0123456789")
                        .role(Role.ADMIN)
                        .build();
                userRepository.save(user);
                log.warn("Admin user has been created with default username: admin, password: admin");
            } else {
                log.info("Admin user already exists");
            }
        };
    }
}