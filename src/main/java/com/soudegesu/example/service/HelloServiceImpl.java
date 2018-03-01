package com.soudegesu.example.service;

public class HelloServiceImpl implements HelloService {

    @Override
    public Thread getCurrentThread() {
        return Thread.currentThread();
    }
}
