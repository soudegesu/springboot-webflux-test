package com.soudegesu.example.mvc.component;

import org.springframework.web.client.RestTemplate;

public interface RestTemplateFactory {
    RestTemplate getRestTemplate();
}
