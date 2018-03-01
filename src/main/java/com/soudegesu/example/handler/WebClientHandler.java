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
        WebClient client = WebClient.create("http://localhost:8080/mono");
        Mono<Value> res = client.get()
                .uri("/")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .flatMap(response -> response.bodyToMono(Value.class));
        return ServerResponse
                .ok()
                .body(res, Value.class)
                .switchIfEmpty(ServerResponse.notFound().build());

    }

    private static class Value {
        private Integer value;

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }
    }
}
