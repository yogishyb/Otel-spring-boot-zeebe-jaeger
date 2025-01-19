package com.yogish.ingester.service;

import io.opentelemetry.instrumentation.annotations.WithSpan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class NumberService {


    @Autowired
    RestTemplate restTemplate;

    public String getNumberTrivia(int number) {
        String url = "http://numbersapi.com/" + number;
        return restTemplate.getForObject(url, String.class);
    }

    @WithSpan
    public String getRandomNumberTrivia() {


        try {
            rep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String url = "http://numbersapi.com/random";

        return restTemplate.getForObject(url, String.class);
    }


    private void rep(int i) throws InterruptedException {
        if (i==0){
            return;
        }
        Thread.sleep(300);
        String url = "http://numbersapi.com/random";
        restTemplate.getForObject(url, String.class);

    }
}
