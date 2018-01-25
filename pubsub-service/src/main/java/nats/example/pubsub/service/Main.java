package nats.example.pubsub.service;

import io.nats.client.Nats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;

/**
 * Starts the NATS Example PubSub Service.
 */
public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String... args) throws Exception {
        LOGGER.info("Starting NATS Example PubSub Service");

        Timer timer = new Timer();
        timer.schedule(new MetricsPublishTask(Nats.connect("nats://localhost:4222")), 0, 1_000);
    }
}
