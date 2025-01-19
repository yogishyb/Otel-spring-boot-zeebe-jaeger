#!/bin/bash

# Check if the OpenTelemetry Collector container exists
if [ "$(docker ps -aq -f name=otel-collector)" ]; then
    # Container exists, restart it
    echo "OpenTelemetry Collector container exists. Restarting..."
    docker restart otel-collector
else
    # Container does not exist, create and run it
    echo "OpenTelemetry Collector container does not exist. Creating and starting..."
    docker run -d --name otel-collector \
      -v $(pwd)/otel/otel-collector-config.yaml:/otel/otel-collector-config.yaml \
      -p 4317:4317 \
      -p 55681:55681 \
      otel/opentelemetry-collector:0.80.0 \
      --config=/otel/otel-collector-config.yaml
fi

# Check if the Jaeger container exists
if [ "$(docker ps -aq -f name=jaeger)" ]; then
    # Container exists, restart it
    echo "Jaeger container exists. Restarting..."
    docker restart jaeger
else
    # Container does not exist, create and run it
    echo "Jaeger container does not exist. Creating and starting..."
    docker run -d --name jaeger \
      -e COLLECTOR_ZIPKIN_HTTP_PORT=9411 \
      -p 5775:5775/udp \
      -p 6831:6831/udp \
      -p 6832:6832/udp \
      -p 5778:5778 \
      -p 16686:16686 \
      -p 14268:14268 \
      -p 14250:14250 \
      -p 9411:9411 \
      jaegertracing/all-in-one:latest
fi

# Path to the OpenTelemetry agent JAR
OTEL_AGENT_PATH="opentelemetry-javaagent.jar"

# OTLP endpoint for Jaeger
OTEL_EXPORTER_ENDPOINT="http://localhost:4317"

# Service name
SERVICE_NAME="Yogish-App"

# Application JAR
APP_JAR="build/libs/demo-0.0.1-SNAPSHOT.jar"

# Start the application with OpenTelemetry agent
#java --add-opens java.base/java.lang=ALL-UNNAMED \
#     --add-opens java.base/java.util=ALL-UNNAMED \
#     --add-opens java.base/java.io=ALL-UNNAMED \
#     -javaagent:$OTEL_AGENT_PATH \
#     -DOTEL_METRICS_EXPORTER=none \
#     -Dotel.traces.exporter=otlp \
#     -Dotel.exporter.otlp.endpoint=$OTEL_EXPORTER_ENDPOINT \
#     -Dotel.service.name=$SERVICE_NAME \
#     -jar $APP_JAR
