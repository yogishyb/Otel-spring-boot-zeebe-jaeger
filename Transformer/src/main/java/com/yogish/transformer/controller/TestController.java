package com.yogish.transformer.controller;


import com.yogish.transformer.service.NumberService;
import io.opentelemetry.api.trace.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TestController {

    @Autowired
    private  Tracer tracer;

    // GlobalOpenTelemetry.getTracer("my-tracer");
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    NumberService numberService;

    @GetMapping("/random")
    public String PDat(){


        return numberService.getRandomNumberTrivia();
    }

    @GetMapping("/data1")
    public String getData() {
        // Start a custom span
      //  Span span = tracer.spanBuilder("getDataSpan").startSpan();
        try {

            // Your business logic here
            return numberService.getRandomNumberTrivia();
        } finally {
           // span.end();
        }
    }





}
