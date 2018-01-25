package nats.example.pubsub.service;

import com.google.gson.Gson;
import io.nats.client.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;
import java.util.TimerTask;

/**
 * Timer task that published system metrics once per second to NATS topic "metrics-pubsub".
 */
public class MetricsPublishTask extends TimerTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(MetricsPublishTask.class);

    private final OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
    private final Gson gson = new Gson();
    private final Connection conn;

    public MetricsPublishTask(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void run() {
        MetricsMessage metricsMessage = new MetricsMessage();
        metricsMessage.cpuPercentage = Math.round(osBean.getSystemCpuLoad() * 100.0) / 100.0;
        metricsMessage.totalPhysicalMemory = osBean.getTotalPhysicalMemorySize() / 1024 / 1024;
        metricsMessage.freePhysicalMemory = osBean.getFreePhysicalMemorySize() / 1024 / 1024;

        try {
            String msg = gson.toJson(metricsMessage);

            LOGGER.info("Publishing: " + msg);
            conn.publish("metrics-pubsub", gson.toJson(msg).getBytes());
        } catch (IOException e) {
            LOGGER.error("Error publishing metrics", e);
        }
    }

    /**
     * Message published to clients containing system metrics.
     */
    private static class MetricsMessage {
        double cpuPercentage;
        double totalPhysicalMemory;
        double freePhysicalMemory;
    }
}
