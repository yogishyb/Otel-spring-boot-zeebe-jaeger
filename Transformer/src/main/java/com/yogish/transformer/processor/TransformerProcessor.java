package com.yogish.transformer.processor;

import com.yogish.transformer.service.NumberService;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
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




    private final static Logger LOG = LoggerFactory.getLogger(TransformerProcessor.class);

    @JobWorker(type = "trans")
    public Map<String, Object> chargeCreditCard(final ActivatedJob job) {
        Map<String, Object> variables = job.getVariablesAsMap();
        String traceId = (String) variables.get("traceId");

String name = variables.get("name").toString();
        LOG.info("charging credit card: {}, trace : {}", name,traceId);



        return Map.of("amountCharged", name,"number",numberService.getRandomNumberTrivia());
    }

}
