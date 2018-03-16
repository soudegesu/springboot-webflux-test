package com.soudegesu.example.webflux.handler;

import com.soudegesu.example.webflux.response.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

@Component
public class HelloWebClientHandler {

    @Value("${app.backend.uri}")
    private String baseUri;

    private static final String PATH = "/test";

    public RouterFunction<ServerResponse> routes() {
        return RouterFunctions.route(
                RequestPredicates.GET("/hello")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON))
                , this::webclient);
    }

    private Mono<ServerResponse> webclient(ServerRequest req) {
        return WebClient.builder()
                .baseUrl(baseUri)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .build()
                .get()
                .uri(uriBuilder -> {
                    uriBuilder.path(PATH);
                    if (req.queryParam("time").isPresent()) {
                        uriBuilder.queryParam("time", req.queryParam("time").get());
                    }
                    return uriBuilder.build();
                })
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .flatMap(response ->
                    ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(response.bodyToMono(User.class), User.class)
                            .switchIfEmpty(ServerResponse.notFound().build())
                );
    }
}
