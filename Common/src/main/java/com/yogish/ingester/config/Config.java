package com.yogish.ingester.config;

//import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
public class Config {

    @Bean
    public RestTemplate restTemplate( ) {
        RestTemplate restTemplate = new RestTemplate();

     //   restTemplate.setInterceptors(List.of(new TraceContextIntercept()));
        return restTemplate;
    }

//    @Bean
//    public OtlpGrpcSpanExporter otlpHttpSpanExporter(@Value("${tracing.url}") String url) {
//        return OtlpGrpcSpanExporter.builder().setEndpoint(url).build();
//    }
}
