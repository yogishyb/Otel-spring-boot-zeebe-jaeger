#!/bin/bash



# Check if the Jaeger container exists
if [ "$(docker ps -aq -f name=jaeger)" ]; then
    # Container exists, restart it
    echo "Jaeger container exists. Restarting..."
    docker restart jaeger
else
    # Container does not exist, create and run it
    echo "Jaeger container does not exist. Creating and starting..."
    docker run --rm --name jaeger \
      -e COLLECTOR_OTLP_ENABLED=true \
      -p 16686:16686 \
      -p 4317:4317 \
      -p 4318:4318 \
      -p 5778:5778 \
      -p 9411:9411 \
      jaegertracing/jaeger:2.2.0
fi