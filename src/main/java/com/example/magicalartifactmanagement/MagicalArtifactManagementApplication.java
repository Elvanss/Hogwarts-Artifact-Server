package com.example.magicalartifactmanagement;

import com.example.magicalartifactmanagement.Ultis.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MagicalArtifactManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(MagicalArtifactManagementApplication.class, args);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker(1, 1);
    }

}
