package com.example.identityserver.infrastucture;

import com.example.identityserver.domain.User;
import com.example.identityserver.domain.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SeedRunner implements CommandLineRunner {

    UserRepository userRepository;
    PasswordEncoder pwdEncoder;

    @Override
    public void run(String... args) throws Exception {

        if (userRepository.count() > 0) {
            return;
        }

        userRepository.save(new User("demo", pwdEncoder.encode("demo"), UserRole.ADMIN));
    }
}
