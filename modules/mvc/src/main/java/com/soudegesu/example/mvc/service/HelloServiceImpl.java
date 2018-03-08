package com.soudegesu.example.mvc.service;

import com.soudegesu.example.mvc.component.RestTemplateFactory;
import com.soudegesu.example.mvc.response.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HelloServiceImpl implements HelloService {

    private static final String URI = "http://localhost:9080/test";

    @Autowired
    private RestTemplateFactory restTemplateFactory;

    @Override
    public User getUser() {

        RestTemplate restTemplate = restTemplateFactory.getRestTemplate();
        User user = restTemplate.getForObject(URI, User.class);

        return user;
    }
}
