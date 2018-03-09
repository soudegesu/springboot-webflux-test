package com.soudegesu.example.webflux.service;

import org.springframework.stereotype.Service;

@Service
public interface HelloService {
    Thread getCurrentThread();
}
