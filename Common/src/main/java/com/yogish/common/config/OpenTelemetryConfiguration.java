package com.yogish.common.config;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentelemetry.semconv.ServiceAttributes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.concurrent.TimeUnit;

/**
 * reference <a href="https://github.com/open-telemetry/opentelemetry-java-examples/tree/main/jaeger/src/main/java/io/opentelemetry/example/jaeger">Jaeger</a>
 */

@Configuration
class OpenTelemetryConfiguration {

    @Value("${spring.application.name}")
    String appName;

    @Bean
    @Lazy
    public OpenTelemetry openTelemetry(@Value("${exporter.jaeger.grpc.endpoint:http://localhost:4317}") String jaegerEndpoint) {
        // Export traces to Jaeger over OTLP
        OtlpGrpcSpanExporter jaegerOtlpExporter =
                OtlpGrpcSpanExporter.builder()
                        .setEndpoint(jaegerEndpoint)
                        .setTimeout(30, TimeUnit.SECONDS)
                        .build();

        Resource serviceNameResource =
                Resource.create(Attributes.of(ServiceAttributes.SERVICE_NAME, appName));

        // Set to process the spans by the Jaeger Exporter
        SdkTracerProvider tracerProvider =
                SdkTracerProvider.builder()
                        .addSpanProcessor(BatchSpanProcessor.builder(jaegerOtlpExporter).build())
                        .setResource(Resource.getDefault().merge(serviceNameResource))
                        .build();
        OpenTelemetrySdk openTelemetry =
                OpenTelemetrySdk.builder().setTracerProvider(tracerProvider).build();

        // it's always a good idea to shut down the SDK cleanly at JVM exit.
        Runtime.getRuntime().addShutdownHook(new Thread(tracerProvider::close));

        return openTelemetry;
    }

    @Bean
    public Tracer tracer(OpenTelemetry openTelemetry){
        return openTelemetry.getTracer("Ingestion");
    }


}