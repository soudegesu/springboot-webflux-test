package com.soudegesu.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class HelloWebFluxApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(HelloWebFluxApplication.class, args);
        run.close();
    }

}
