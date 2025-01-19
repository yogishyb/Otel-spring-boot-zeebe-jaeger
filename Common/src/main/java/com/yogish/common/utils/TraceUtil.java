package com.yogish.common.utils;




import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanContext;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.propagation.TextMapSetter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TraceUtil {
    public static String getCurrentTraceId() {
        Span currentSpan = Span.current();
        SpanContext spanContext = currentSpan.getSpanContext();
        Tracer tracer;

        return spanContext.getTraceId();
    }


    public static Map<String, String> getTraceContextMap(){
        Span currentSpan = Span.current();
        SpanContext spanContext = currentSpan.getSpanContext();
        Context context = Context.current();

        // Prepare a map to hold the trace context
        Map<String, String> traceContextMap = new HashMap<>();

        // Define a TextMapSetter to inject the trace context into the map
        TextMapSetter<Map<String, String>> setter = (carrier, key, value) -> carrier.put(key, value);

        // Inject the trace context into the map
        GlobalOpenTelemetry.getPropagators().getTextMapPropagator().inject(Context.current(), traceContextMap, setter);
        W3CTraceContextPropagator.getInstance().inject(context,traceContextMap,setter);
return traceContextMap;
    }
}

