package com.yogish.common.config;

//import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
import io.camunda.zeebe.client.ZeebeClient;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.propagation.TextMapPropagator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Configuration
public class Config {

    @Bean
    public RestTemplate restTemplate( ) {
        RestTemplate restTemplate = new RestTemplate();

     //   restTemplate.setInterceptors(List.of(new TraceContextIntercept()));
        return restTemplate;
    }
    @Bean
    public TextMapPropagator textMapPropagator() {
        // Use W3C Trace Context Propagator (standard for trace context propagation)
        return W3CTraceContextPropagator.getInstance();
    }

/*
    @Bean
   public OtlpGrpcSpanExporter otlpHttpSpanExporter(@Value("${tracing.url}") String url) {
        return OtlpGrpcSpanExporter.builder().setEndpoint(url).build();
    }
*/

    @Bean
        @Primary
    public ZeebeClient zeebeClient1() throws URISyntaxException {
        URI uri = new URI("http://127.0.0.1:26500");
        return ZeebeClient.newClientBuilder().grpcAddress(uri) // Replace with your Zeebe broker address
                .usePlaintext()
                .build();
    }

//    @Bean
//    public OtlpGrpcSpanExporter otlpHttpSpanExporter(@Value("${tracing.url}") String url) {
//        return OtlpGrpcSpanExporter.builder().setEndpoint(url).build();
//    }
}
