package com.soudegesu.example.service;

import java.util.Random;

public class HelloServiceImpl implements HelloService {

    private static Random RANDOM = new Random();

    @Override
    public String getCodeStr() {
        return String.valueOf(RANDOM.nextInt());
    }
}
