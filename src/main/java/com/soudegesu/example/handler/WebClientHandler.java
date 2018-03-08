package com.soudegesu.example.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

@Component
public class WebClientHandler {


    public RouterFunction<ServerResponse> routes() {
        return RouterFunctions.route(
                RequestPredicates.GET("/webclient/{id}")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON))
                , this::webclient);
    }

    private Mono<ServerResponse> webclient(ServerRequest req) {
        WebClient client = WebClient.builder()
                .baseUrl("http://localhost:9080/")
                .build();

        Mono<User> res = client.get()
                .uri("/test")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .log()
                .flatMap(response -> response.bodyToMono(User.class))
                .log();

        return ServerResponse
                .ok()
                .body(res, User.class)
                .switchIfEmpty(ServerResponse.notFound().build());

    }

    private static class User {
        private Integer id;

        private String name;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
