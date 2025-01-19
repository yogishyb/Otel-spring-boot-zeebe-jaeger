package com.yogish.transformer.service;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class NumberService {


    private static final Logger log = LoggerFactory.getLogger(NumberService.class);
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    Tracer tracer;

    public String getNumberTrivia(int number) {
        String url = "http://numbersapi.com/" + number;
        return restTemplate.getForObject(url, String.class);
    }

    @WithSpan
    public String getRandomNumberTrivia() {


//        try {
//            rep(10);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        String url = "http://numbersapi.com/random";
       // restTemplate.getForObject(url, String.class);
        return getRandomNumberTrivia2();

    }

    @WithSpan
    public String getRandomNumberTrivia2() {
        String s = "";
    
        try {
            rep(10);
            String url = "http://numbersapi.com/random";
             s= restTemplate.getForObject(url, String.class);
        } catch (Exception e) {

        }
       
        return s;
       
    }



    private void rep(int i) throws InterruptedException {
        if (i==0){
            return;
        }

        Span span = tracer.spanBuilder("Span in rep "+ i).startSpan();
        try(Scope scope=span.makeCurrent()){
            Thread.sleep(300);
            String url = "http://numbersapi.com/random";
            restTemplate.getForObject(url, String.class);
        }finally {
            span.end();
            rep(i-1);
        }




    }
}
