package com.yogish.transformer.processor;

import com.yogish.transformer.service.NumberService;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import io.opentelemetry.context.propagation.TextMapGetter;
import io.opentelemetry.context.propagation.TextMapPropagator;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Component
public class TransformerProcessor {

    @Autowired
    NumberService numberService;


    @Autowired
    TextMapPropagator propagator;

    @Autowired
    private Tracer tracer;


    private final static Logger LOG = LoggerFactory.getLogger(TransformerProcessor.class);

    @JobWorker(type = "trans")

    public Map<String, Object> chargeCreditCard(final ActivatedJob job) {
        Map<String, Object> variables = job.getVariablesAsMap();




        Map<String, String> contextMap = (Map<String, String>) variables.get("traceContext");

        Context extractedContext = propagator.extract(Context.current(), contextMap, new TextMapGetter<Map<String, String>>() {
            @Override
            public Iterable<String> keys(Map<String, String> career) {
                return career.keySet();
            }

            @Override
            public @Nullable String get(@Nullable Map<String, String> career, String key) {
                return career.get(key);
            }
        });
        Span span = tracer.spanBuilder("chargeCreditCard with instance %s".formatted(String.valueOf(job.getProcessInstanceKey())))
                //.setParent(Context.current())
                .setParent(extractedContext)
                .startSpan();
        LOG.info("Process instance key : {}",job.getProcessInstanceKey());
        try(Scope scope= span.makeCurrent()){

            businessPocess(variables);
        }finally {
            span.end();
        }


        // Start a new span that continues from the extracted context







        return variables;
    }

    @WithSpan
    private void businessPocess(Map<String, Object> variables){
        Span.current();
        String name = variables.get("name").toString();
        //LOG.info("charging credit card: {}, trace : {}", name,traceId);
        variables.put("amountCharged", name);
        variables.put("number",numberService.getRandomNumberTrivia());
    }

    @JobWorker(type = "parse")
    //@WithSpan
    public Map<String, Object> parsee(final ActivatedJob job) {
        Map<String, Object> variables = job.getVariablesAsMap();
        String traceId = (String) variables.get("traceId");

        String name = variables.get("name").toString();
        LOG.info("charging credit card: {}, trace : {}", name,traceId);



        return Map.of("amountCharged", name);
    }

}
