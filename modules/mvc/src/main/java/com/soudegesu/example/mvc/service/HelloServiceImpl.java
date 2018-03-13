package com.soudegesu.example.mvc.service;

import com.soudegesu.example.mvc.component.RestTemplateFactory;
import com.soudegesu.example.mvc.response.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
public class HelloServiceImpl implements HelloService {

    @Value("${app.backend.uri}")
    private String baseUri;

    private static final String PATH = "/test";

    private static final List<MediaType> MEDIA_TYPES = new ArrayList() {{
        add(MediaType.APPLICATION_JSON);
    }};

    @Autowired
    private RestTemplateFactory restTemplateFactory;

    @Override
    public User getUser(Double time) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(MEDIA_TYPES);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUri + PATH);
        if (time != null) {
            builder.queryParam("time", time);
        }

        HttpEntity<User> response = restTemplateFactory.getRestTemplate()
                .exchange(builder.build().encode().toUri(),
                        HttpMethod.GET,
                        new HttpEntity(headers),
                        User.class);

        return response.getBody();
    }
}
