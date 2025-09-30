package com.example.anomalyapi.service;

import com.example.anomalyapi.model.Metric;
import com.example.anomalyapi.model.MetricRequest;
import com.example.anomalyapi.model.Anomaly;
import com.example.anomalyapi.repository.MetricRepository;
import com.example.anomalyapi.repository.AnomalyRepository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.List;

@Service
public class MetricService {
    private final MetricRepository metricrepository;
    private final AnomalyRepository anomalyrepository;

        // Defaults (can later make configurable via application.properties)
    private static final int DEFAULT_WINDOW = 30;
    private static final double DEFAULT_THRESHOLD = 3.0;

    public MetricService(MetricRepository metricrepository,AnomalyRepository anomalyrepository) {
        this.metricrepository = metricrepository;
        this.anomalyrepository = anomalyrepository;
    }

    public List<Metric> getMetrics(String name, Long from, Long to, int limit) {
        Pageable pageable = PageRequest.of(0, limit);

        if (from != null && to != null) {
            Instant fromInstant = Instant.ofEpochMilli(from);
            Instant toInstant = Instant.ofEpochMilli(to);
            return metricrepository.findByNameAndTimestampBetweenOrderByTimestampDesc(
                name, fromInstant, toInstant, pageable);
            } 
            else {
            return metricrepository.findByNameOrderByTimestampDesc(name, pageable);
    }
    }

    public Metric saveMetric(MetricRequest request) {
        Metric metric = new Metric();
        metric.setName(request.getMetricName());
        metric.setValue(request.getValue());

        // if timestamp missing, use current time
        if (request.getTimestamp() != null) {
            metric.setTimestamp(Instant.ofEpochMilli(request.getTimestamp()));
        } else {
            metric.setTimestamp(Instant.now());
        }

        Metric saved = metricrepository.save(metric);

              // Get recent values for this metric
        List<Metric> pastMetrics = metricrepository.findByNameOrderByTimestampDesc(
                metric.getName(),
                PageRequest.of(0, DEFAULT_WINDOW)
        );
        List<Double> pastValues = pastMetrics.stream()
                .map(Metric::getValue)
                .toList();
      

        // Detect anomaly
        Anomaly anomaly = AnomalyDetector.detect(
                metric.getName(),
                metric.getValue(),
                metric.getTimestamp(),
                pastValues,
                DEFAULT_WINDOW,
                DEFAULT_THRESHOLD
        );

        if (anomaly != null) {
            anomalyrepository.save(anomaly);

            // Also log alert
            logAnomaly(anomaly);
        }

        return saved;
    }

    // Log anomaly class 
        private static final Logger log = LoggerFactory.getLogger(MetricService.class);

        private void logAnomaly(Anomaly anomaly) {
            log.warn("[ALERT] {} anomaly detected: {} at {}. Reason: {}",
            anomaly.getMetricName(),
            anomaly.getValue(),
            anomaly.getTimestamp(),
            anomaly.getReason());
        }
    

    public List<Metric> getLastMetrics() {
    return metricrepository.findTop10ByOrderByTimestampDesc();
    }
}