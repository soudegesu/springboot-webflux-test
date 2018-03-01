package com.soudegesu.example.repository;

import com.soudegesu.example.data.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MongoRepository extends ReactiveCrudRepository<User, String> {

}
