package com.soudegesu.example.webflux.service;

public class HelloServiceImpl implements HelloService {

    @Override
    public Thread getCurrentThread() {
        return Thread.currentThread();
    }
}
