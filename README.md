# nats-pubsub-example
[![Build Status](https://travis-ci.org/gregwhitaker/nats-pubsub-example.svg?branch=master)](https://travis-ci.org/gregwhitaker/nats-pubsub-example)

An example of using [NATS](https://nats.io) to do pubsub messaging.

This example starts a service that publishes CPU metrics every second to a `metrics-pubsub` topic. The topic is subscribed to by one-to-many clients each receiving the CPU metrics messages published by the service.

## Background
NATS publish subscribe is a one-to-many communication. A publisher sends a message on a subject. Any active subscriber listening on that subject receives the message. 

The NATS pubsub model is "At-Most-Once-Delivery". If a subscriber is not listening on the subject (no subject match), or is not active when the message is sent, the message is not received. 

More information can be found in the [NATS Documentation](https://nats.io/documentation/concepts/nats-pub-sub/).

## Prerequisites
The examples require a local NATS server to be running. To start a NATS server as a Docker container run the following commands:

    $ docker pull nats
    $ docker run -p 4222:4222 -p 6222:6222 -p 8222:8222 -d --name nats-main nats

## Running the Example
### Start the PubSub Client
You can start the [PubSub Client](pubsub-client/README.md) using the following command:

    $ ./gradlew :pubsub-client:run
    
Once the client and service is running you will see messages similar to the following in the terminal:

    [main] INFO nats.example.pubsub.client.Main - Starting NATS Example PubSub Client
    [jnats-subscriptions] INFO nats.example.pubsub.client.Main - Received Message: "{\"cpuPercentage\":0.0,\"totalPhysicalMemory\":16384.0,\"freePhysicalMemory\":525.0}"
    [jnats-subscriptions] INFO nats.example.pubsub.client.Main - Received Message: "{\"cpuPercentage\":0.27,\"totalPhysicalMemory\":16384.0,\"freePhysicalMemory\":454.0}"
    [jnats-subscriptions] INFO nats.example.pubsub.client.Main - Received Message: "{\"cpuPercentage\":0.06,\"totalPhysicalMemory\":16384.0,\"freePhysicalMemory\":362.0}"

You can start multiple client instances to see them each receiving the same messages.

### Start the PubSub Service
You can start the [PubSub Service](pubsub-service/README.md) using the following command:

    $ ./gradlew :pubsub-service:run

Once the service is running you will see messages similar to the following in the terminal:

    [main] INFO nats.example.pubsub.service.Main - Starting NATS Example PubSub Service
    [Timer-0] INFO nats.example.pubsub.service.MetricsPublishTask - Publishing: {"cpuPercentage":0.0,"totalPhysicalMemory":16384.0,"freePhysicalMemory":552.0}
    [Timer-0] INFO nats.example.pubsub.service.MetricsPublishTask - Publishing: {"cpuPercentage":0.28,"totalPhysicalMemory":16384.0,"freePhysicalMemory":448.0}
    [Timer-0] INFO nats.example.pubsub.service.MetricsPublishTask - Publishing: {"cpuPercentage":0.04,"totalPhysicalMemory":16384.0,"freePhysicalMemory":564.0}

## Bugs and Feedback
For bugs, questions and discussions please use the [Github Issues](https://github.com/gregwhitaker/nats-pubsub-example/issues).

## License
MIT License

Copyright (c) 2018 Greg Whitaker

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.