package com.improvement.movieflix;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MovieFlixApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MovieFlixApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Hello word");

    }
}
