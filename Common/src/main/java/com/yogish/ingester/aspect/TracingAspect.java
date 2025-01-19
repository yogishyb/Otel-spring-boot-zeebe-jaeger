//package com.example.demo.aspect;
//
//import com.example.demo.config.Config;
//import io.opentelemetry.api.trace.Span;
//import io.opentelemetry.api.trace.SpanContext;
//import io.opentelemetry.api.trace.Tracer;
//import io.opentelemetry.context.Context;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//@Lazy
//public class TracingAspect {
//
//    private  Tracer tracer;
//
//
//
//    @Autowired
//    public void setTracer(Tracer tracer) {
//        this.tracer = tracer;
//    }
//
//    @Around("execution(* com.example..*(..))") // Adjust the pointcut to match your methods
//    public Object traceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
//        // Get the parent span from the current context
//        SpanContext parentSpan = Span.current().getSpanContext();
//
//        // Create a child span for this method execution
//        Span childSpan = tracer.spanBuilder(joinPoint.getSignature().toShortString())
//
//                .setParent((Context) parentSpan)  // Set the parent span
//                .startSpan();
//
//
//        try (var scope = childSpan.makeCurrent()) {
//            // Proceed with the method execution and return the result
//            return joinPoint.proceed();
//        } finally {
//            childSpan.end();  // End the child span
//        }
//    }
//}
//
