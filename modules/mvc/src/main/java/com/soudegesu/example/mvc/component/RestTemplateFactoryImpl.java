package com.soudegesu.example.mvc.component;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateFactoryImpl implements RestTemplateFactory {

    @Override
    public RestTemplate getRestTemplate() {

        CloseableHttpClient httpClient = HttpClients.custom().build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setReadTimeout(100);
        requestFactory.setConnectTimeout(10);
        requestFactory.setConnectionRequestTimeout(100);
        requestFactory.setHttpClient(httpClient);
        requestFactory.setBufferRequestBody(true);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(requestFactory);
        return restTemplate;
    }
}
