package org.grocery.groceryshop.configuration;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.grocery.groceryshop.entity.Users;
import org.grocery.groceryshop.enums.Role;
import org.grocery.groceryshop.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository){
        return args -> {
            if(userRepository.findByUsername("admin").isEmpty()){
                var roles = new HashSet<String>();
                roles.add(Role.ADMIN.name());
                Users user = Users.builder()
                        .email("admin@gmail.com")
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles(roles)
                        .status(1)
                        .role("1")
                        .build();
                userRepository.save(user);
                log.warn("Default admin user has been created with default password: admin");

            }else{
                log.warn("Created failed with account admin");
            }

        };
    }
}
