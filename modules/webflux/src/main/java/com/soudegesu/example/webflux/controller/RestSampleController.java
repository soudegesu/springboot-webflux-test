package com.soudegesu.example.webflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Stream;

@RestController
public class RestSampleController {

    @GetMapping("/mono")
    Mono<Map<String, Integer>> mono() {
        return Mono.just(Collections.singletonMap("value", 1));
    }

    @GetMapping("/stream")
    Flux<Map<String, Integer>> stream() {
        Stream<Integer> stream = Stream.iterate(0, i -> i + 1);
        return Flux.fromStream(stream.limit(10))
                .map(i -> Collections.singletonMap("value", i));
    }
}
