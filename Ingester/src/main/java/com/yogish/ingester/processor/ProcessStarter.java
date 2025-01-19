package com.yogish.ingester.processor;

import com.yogish.ingester.utils.TraceUtil;
import io.camunda.operate.CamundaOperateClient;
import io.camunda.operate.exception.OperateException;
import io.camunda.operate.model.ProcessDefinition;
import io.camunda.operate.search.ProcessDefinitionFilter;
import io.camunda.operate.search.SearchQuery;
import io.camunda.operate.search.Sort;
import io.camunda.operate.search.SortOrder;
import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.DeploymentEvent;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;


@Component
public class ProcessStarter {

    @Autowired
    CamundaOperateClient camundaOperateClient;

    private final ZeebeClient zeebeClient;

    public ProcessStarter(ZeebeClient zeebeClient) {
        this.zeebeClient = zeebeClient;
    }


    @WithSpan
    public void startProcessInstance(String processId, Map<String, Object> variables) {
        String traceId = TraceUtil.getCurrentTraceId();
        variables.put("traceId", traceId);


        zeebeClient.newCreateInstanceCommand()
                .bpmnProcessId(processId)
                .latestVersion()
                .variables(variables)
                .send()
                .join();
    }

    public void getallWorkFLows() throws OperateException {


        ProcessDefinitionFilter processDefinitionFilter = ProcessDefinitionFilter.builder().build();
        SearchQuery procDefQuery = new SearchQuery.Builder()
                .filter(processDefinitionFilter)
                .size(1000)
                .sort(new Sort("version", SortOrder.DESC))
                .build();
        List<ProcessDefinition> list = camundaOperateClient.searchProcessDefinitions(procDefQuery);;




    }

    public void listDeployedWorkflows() {
        // Fetch deployments
        zeebeClient.newDeployCommand()
                .addResourceFile("path/to/your/bpmn-file.bpmn")
                .send()
                .join();


        DeploymentEvent event= zeebeClient.newDeployResourceCommand().addResourceFile("").send().join();
        // To list deployed workflows, you can maintain records manually as Zeebe does not natively list all deployed workflows.
        event.get();

        zeebeClient.newDeployResourceCommand().

        for (DeploymentEvent deployment : deployments) {
            for (Workflow workflow : deployment.getWorkflows()) {
                System.out.println("Deployed Workflow: " + workflow.getBpmnProcessId());
            }
        }
}
