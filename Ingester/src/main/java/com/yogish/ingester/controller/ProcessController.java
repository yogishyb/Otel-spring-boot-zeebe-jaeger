package com.yogish.ingester.controller;


import com.yogish.ingester.processor.ProcessStarter;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanContext;
import io.opentelemetry.context.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class ProcessController {

    @Autowired
    ProcessStarter processStarter;


    @GetMapping("/process")
    public long process() {




        try {
            HashMap<String, Object> map = new HashMap<>();
            map.put("name", "yogish");

            return processStarter.startProcessInstance("Process_1yvmqaq", map);
        } finally {

        }
    }
}
