package com.yogish.ingester.controller;


import com.yogish.ingester.processor.ProcessStarter;
import com.yogish.ingester.utility.SpanContextStore;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class ProcessController {

    @Autowired
    ProcessStarter processStarter;

    @Autowired
    SpanContextStore contextStore;

    @Autowired
    Tracer tracer;


    @GetMapping("/process")
    public long process() {

        Span span = tracer.spanBuilder("GET /procesInn").startSpan();







        try (Scope _ =span.makeCurrent()){
            contextStore.addContext("1st",Context.current());
            HashMap<String, Object> map = new HashMap<>();
            span.setAttribute("mid","yogish");
            map.put("name", "yogish");

            return processStarter.startProcessInstance("Process_1yvmqaq", map);
        } finally {
span.end();
        }
    }
}
