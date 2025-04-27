package com.ali.movieapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ali.movieapp"})
@EntityScan("com.ali.movieapp.model")
@EnableJpaRepositories("com.ali.movieapp.repository")
public class MovieAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(MovieAppApplication.class, args);
    }
}