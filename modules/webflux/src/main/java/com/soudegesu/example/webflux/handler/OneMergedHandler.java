package com.soudegesu.example.webflux.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class OneMergedHandler {

    public RouterFunction<ServerResponse> routes() {
        return RouterFunctions.route(RequestPredicates.GET("/one"), this::hello);
    }

    private Mono<ServerResponse> hello(ServerRequest req) {

        return ServerResponse.ok().body(Flux.just("one", "ok"), String.class);
    }

}
