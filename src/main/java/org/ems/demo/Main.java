package org.ems.demo;

import org.ems.demo.entity.UserRoleEntity;
import org.ems.demo.repository.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "org.ems.demo.repository")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }

    @Bean
    CommandLineRunner initializeRoles(UserRoleRepository userRoleRepository) {
        return args -> {
            // Check and add ROLE_USER
            if (userRoleRepository.findByName("ROLE_USER").isEmpty()) {
                UserRoleEntity userRoleEntity = new UserRoleEntity().setName("ROLE_USER");
                userRoleRepository.save(userRoleEntity);
            }

            // Check and add ROLE_ADMIN
            if (userRoleRepository.findByName("ROLE_ADMIN").isEmpty()) {
                UserRoleEntity adminUserRoleEntity = new UserRoleEntity().setName("ROLE_ADMIN");
                userRoleRepository.save(adminUserRoleEntity);
            }

            // Check and add ROLE_EMP
            if (userRoleRepository.findByName("ROLE_EMP").isEmpty()) {
                UserRoleEntity adminUserRoleEntity = new UserRoleEntity().setName("ROLE_EMP");
                userRoleRepository.save(adminUserRoleEntity);
            }
        };
    }
}