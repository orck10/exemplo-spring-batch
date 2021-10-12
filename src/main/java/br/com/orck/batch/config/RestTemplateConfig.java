package br.com.orck.batch.config;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import br.com.orck.batch.error.RestTemplateResponseErrorHandler;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(){
    	
    	CloseableHttpClient httpClient = HttpClients.custom().build();

    	HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

    	requestFactory.setHttpClient(httpClient);
    	RestTemplate restTemplate = new RestTemplate(requestFactory);
    	restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
    	return restTemplate;
    }
}