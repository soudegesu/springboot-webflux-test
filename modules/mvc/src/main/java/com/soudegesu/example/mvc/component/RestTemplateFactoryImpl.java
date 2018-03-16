package com.soudegesu.example.mvc.component;

import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Component
public class RestTemplateFactoryImpl implements RestTemplateFactory {

    private static final int DAFAULT_AMX_PER_ROUTE = 200;

    private static final int MAX_TOTAL_CONNECTION = 200;

    private static final int VALIDATE_AFTER_INACTIVITY = 100;

    /** 1s => 1000ms */
    private static final int SECOND_TO_MILLISEC = 1000;

    private PoolingHttpClientConnectionManager connectionManager;

    @PostConstruct
    private void init() {
        connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(MAX_TOTAL_CONNECTION);
        connectionManager.setDefaultMaxPerRoute(DAFAULT_AMX_PER_ROUTE);
        connectionManager.setValidateAfterInactivity(VALIDATE_AFTER_INACTIVITY);

        SocketConfig socketConfig = SocketConfig.custom()
                .setTcpNoDelay(true)
                .setSoKeepAlive(true)
                .setSoReuseAddress(true)
                .setSoLinger(5)
                .build();
        connectionManager.setDefaultSocketConfig(socketConfig);
    }

    @Override
    public RestTemplate getRestTemplate() {

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .disableAuthCaching()
                .disableCookieManagement()
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setReadTimeout(100 * SECOND_TO_MILLISEC);
        requestFactory.setConnectTimeout(5 * SECOND_TO_MILLISEC);
        requestFactory.setConnectionRequestTimeout(5 * SECOND_TO_MILLISEC);
        requestFactory.setHttpClient(httpClient);
        requestFactory.setBufferRequestBody(true);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(requestFactory);
        return restTemplate;
    }
}
