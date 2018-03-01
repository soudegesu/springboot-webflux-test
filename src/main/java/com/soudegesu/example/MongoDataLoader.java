package com.soudegesu.example;

import com.soudegesu.example.data.User;
import com.soudegesu.example.repository.MongoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
public class MongoDataLoader implements CommandLineRunner {

    private MongoRepository mongoRepository;

    public MongoDataLoader(MongoRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (mongoRepository.count().block() == 0L) {
            mongoRepository.deleteAll();
            mongoRepository.save(new User("1", "name", "address"));
        }
    }
}
