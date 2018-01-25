# pubsub-service
Starts an example NATS service that publishes system metrics every second as a JSON object.

Example:

    {"cpuPercentage":0.0,"totalPhysicalMemory":16384.0,"freePhysicalMemory":552.0}
    
## Running the Service
You can run the pubsub example service using the following command:

    $ ../gradlew :pubsub-service:run