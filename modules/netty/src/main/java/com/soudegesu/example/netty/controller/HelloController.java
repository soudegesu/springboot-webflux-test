package com.soudegesu.example.netty.controller;

import com.soudegesu.example.netty.annotation.RequestMapping;
import io.netty.handler.codec.http.FullHttpRequest;
import org.springframework.stereotype.Controller;

@Controller
public class HelloController {

    @RequestMapping("/hello")
    public Object hello(FullHttpRequest request) {
        return "{\"hogehoge\":\"aaaa\"}";
    }
}
