package com.yogish.ingester.controller;


import com.yogish.ingester.processor.ProcessStarter;
import com.yogish.ingester.service.NumberService;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanContext;
import io.opentelemetry.api.trace.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@RestController
public class TestController {

    @Autowired
    private  Tracer tracer;

    // GlobalOpenTelemetry.getTracer("my-tracer");
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    NumberService numberService;
    @Autowired
    ProcessStarter starter;
    @GetMapping("/random")
    public String PDat(){


        return numberService.getRandomNumberTrivia();
    }

    @GetMapping("/data1")
    public String getData() {

        // Start a custom span
        Span span = tracer.spanBuilder("getDataSpan").startSpan();
        span = Span.current();
        SpanContext spanContext = span.getSpanContext();

// Retrieve the Trace ID as a hexadecimal string
        String traceId = spanContext.getTraceId();
        try {

            // Your business logic here
            return numberService.getRandomNumberTrivia();
        } finally {
           // span.end();
        }
    }

    @GetMapping("/data2")
    public long getData1() {

        Span span = tracer.spanBuilder("getDataSpan").startSpan();
        span = Span.current();
        SpanContext spanContext = span.getSpanContext();

        String traceId = spanContext.getTraceId();
        try {





                HashMap<String, Object> map = new HashMap<>();
                map.put("name","yogish");



            // Your business logic here
          return   starter.startProcessInstance("Process_1yvmqaq",map);
        } finally {
            // span.end();
        }
    }








}
