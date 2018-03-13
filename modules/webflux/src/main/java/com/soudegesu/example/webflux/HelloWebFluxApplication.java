package com.soudegesu.example.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableAutoConfiguration
public class HelloWebFluxApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(HelloWebFluxApplication.class, args); //NOSONAR
        context.registerShutdownHook();
    }

}
