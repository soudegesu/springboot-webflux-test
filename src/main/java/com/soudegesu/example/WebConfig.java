package com.soudegesu.example;

import com.soudegesu.example.handler.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

@Configuration
@ComponentScan("com.soudegesu.example")
@EnableWebFlux
public class WebConfig implements WebFluxConfigurer {
    /**
     * Basic Router Function
     * @return
     */
    @Bean
    RouterFunction<ServerResponse> routes() {
        return RouterFunctions.route(RequestPredicates.GET("/"),
                req -> ServerResponse.ok().body(Flux.just("Hello", "World!!", "PiyoPiyp"), String.class));
    }

    /**
     * In case of using BodyInserters
     * @return
     */
    @Bean
    RouterFunction<ServerResponse> routes2() {
        return RouterFunctions.route(RequestPredicates.GET("/piyo"),
                req -> ServerResponse.ok().body(BodyInserters.fromPublisher(Flux.just("piyo", "world"), String.class)));
    }

    /**
     * In case of using Handler
     * @param helloHandler
     * @return
     */
    @Bean
    RouterFunction<ServerResponse> routes3(HelloHandler helloHandler) {
        return RouterFunctions.route(RequestPredicates.GET("/handler"), helloHandler::hello);
    }

    /**
     * In case of wrapping routing settings with handler
     * @param helloHandlerWithRoutes
     * @return
     */
    @Bean
    RouterFunction<ServerResponse> route4(HelloHandlerWithRoutes helloHandlerWithRoutes) {
        return helloHandlerWithRoutes.routes();
    }

    /**
     * In case of merging routing setting
     * @param oneMergedHandler
     * @param twoMergedHandler
     * @return
     */
    @Bean
    RouterFunction<ServerResponse> route5(OneMergedHandler oneMergedHandler, TwoMergedHandler twoMergedHandler) {
        return oneMergedHandler.routes().and(twoMergedHandler.routes());
    }

    /**
     * In case of using stream
     * @param streamHelloHandler
     * @return
     */
    @Bean
    RouterFunction<ServerResponse> route6(StreamHelloHandler streamHelloHandler) {
        return streamHelloHandler.routes();
    }
}
