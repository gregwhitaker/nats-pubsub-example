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
 *
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
        metricsMessage.setCpuPercentage(Math.round(osBean.getSystemCpuLoad() * 100.0) / 100.0);
        metricsMessage.setTotalPhysicalMemory(osBean.getTotalPhysicalMemorySize() / 1024 / 1024);
        metricsMessage.setFreePhysicalMemory(osBean.getFreePhysicalMemorySize() / 1024 / 1024);

        try {
            String msg = gson.toJson(metricsMessage);

            LOGGER.info("Publishing: " + msg);
            conn.publish("metrics-pubsub", gson.toJson(msg).getBytes());
        } catch (IOException e) {
            LOGGER.error("Error publishing metrics", e);
        }
    }

    /**
     *
     */
    private static class MetricsMessage {
        private double cpuPercentage;
        private double totalPhysicalMemory;
        private double freePhysicalMemory;

        public double getCpuPercentage() {
            return cpuPercentage;
        }

        public void setCpuPercentage(double cpuPercentage) {
            this.cpuPercentage = cpuPercentage;
        }

        public double getTotalPhysicalMemory() {
            return totalPhysicalMemory;
        }

        public void setTotalPhysicalMemory(double totalPhysicalMemory) {
            this.totalPhysicalMemory = totalPhysicalMemory;
        }

        public double getFreePhysicalMemory() {
            return freePhysicalMemory;
        }

        public void setFreePhysicalMemory(double freePhysicalMemory) {
            this.freePhysicalMemory = freePhysicalMemory;
        }
    }
}
