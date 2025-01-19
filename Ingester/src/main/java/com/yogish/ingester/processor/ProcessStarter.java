package com.yogish.ingester.processor;

import com.yogish.common.utils.TraceUtil;
import com.yogish.ingester.utility.SpanContextStore;
import io.camunda.operate.exception.OperateException;
import io.camunda.operate.search.ProcessDefinitionFilter;
import io.camunda.operate.search.SearchQuery;
import io.camunda.operate.search.Sort;
import io.camunda.operate.search.SortOrder;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import io.opentelemetry.context.propagation.TextMapPropagator;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Currency;
import java.util.Map;


@Component
public class ProcessStarter {

//    @Autowired
//    CamundaOperateClient camundaOperateClient;

    @Autowired
    @Qualifier("zeebeClient1")
    private  ZeebeClient zeebeClient1;

    @Autowired
    private  TextMapPropagator propagator;

    @Autowired
    Tracer tracer;


    @Autowired
    SpanContextStore contextStore;



    @WithSpan
    public long startProcessInstance(String processId, Map<String, Object> variables) {


//        Span currentSpan = Span.current();
//        Context currentContext = Context.current();
//
//        Map<String, String> contextMap = new HashMap<>();

        // Inject the current trace context into the map




//        String traceId = TraceUtil.getCurrentTraceId();
//        variables.put("traceId", traceId);


        variables.put("traceContext", TraceUtil.getTraceContextMap());


        ProcessInstanceEvent event =  zeebeClient1.newCreateInstanceCommand()
                .bpmnProcessId(processId)
                .latestVersion()
                .variables(variables)
                .send()
                .join();
       long key =  event.getProcessInstanceKey();

        Span.current().updateName("sent  with instance %s".formatted(String.valueOf(key)));

        
        Span inti = Span.fromContext(contextStore.getContext("1st"));
        inti.updateName("sent  with instance %s".formatted(String.valueOf(key)));
        inti.setAttribute("PROCESS_ID",key);

        return event.getProcessInstanceKey();
    }

    public void getallWorkFLows() throws OperateException {


        ProcessDefinitionFilter processDefinitionFilter = ProcessDefinitionFilter.builder().build();
        SearchQuery procDefQuery = new SearchQuery.Builder()
                .filter(processDefinitionFilter)
                .size(1000)
                .sort(new Sort("version", SortOrder.DESC))
                .build();


        //List<ProcessDefinition> list = camundaOperateClient.searchProcessDefinitions(procDefQuery);;




    }

//    public void listDeployedWorkflows() {
//        // Fetch deployments
//        zeebeClient.newDeployCommand()
//                .addResourceFile("path/to/your/bpmn-file.bpmn")
//                .send()
//                .join();
//
//
//        DeploymentEvent event= zeebeClient.newDeployResourceCommand().addResourceFile("").send().join();
//        // To list deployed workflows, you can maintain records manually as Zeebe does not natively list all deployed workflows.
//        event.get();
//
//        zeebeClient.newDeployResourceCommand().
//
//        for (DeploymentEvent deployment : deployments) {
//            for (Workflow workflow : deployment.getWorkflows()) {
//                System.out.println("Deployed Workflow: " + workflow.getBpmnProcessId());
//            }
//        }
}
