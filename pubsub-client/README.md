# pubsub-client
Starts an example NATS client that subscribes to messages published to the `metrics-pubsub` topic and prints recevied messages to the console.

Start multiple clients to see them each receiving the same set of messages.

## Running the Client
The client can be started by running the following command:

    $ ../gradlew :pubsub-client:run