package nats.example.pubsub.service;

import io.nats.client.Connection;
import io.nats.client.Nats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Starts the NATS Example PubSub Service.
 */
public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) throws Exception {
        LOGGER.info("Starting NATS Example PubSub Service");

        Connection conn = Nats.connect("nats://localhost:4222");
    }
}
