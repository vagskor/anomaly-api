package com.example.anomalyapi.service;

import com.example.anomalyapi.model.Metric;
import com.example.anomalyapi.model.MetricRequest;
import com.example.anomalyapi.model.Anomaly;
import com.example.anomalyapi.repository.MetricRepository;
import com.example.anomalyapi.repository.AnomalyRepository;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
public class MetricService {
    private final MetricRepository metricrepository;
    private final AnomalyRepository anomalyrepository;

    public MetricService(MetricRepository metricrepository,AnomalyRepository anomalyrepository) {
        this.metricrepository = metricrepository;
        this.anomalyrepository = anomalyrepository;
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

        detectAnomaly(saved);


        return saved;
    }
       
    private void detectAnomaly(Metric newMetric) {
        int windowSize = 20; // last N values
        List<Metric> recent = metricrepository.findByNameOrderByTimestampDesc(
            newMetric.getName(),
            PageRequest.of(0, windowSize));

        if (recent.size() < 5) {
            return ; // not enough data
        }

        // Compute mean & std dev
    if (recent.size() < 5) return; // not enough data

    double mean = recent.stream().mapToDouble(Metric::getValue).average().orElse(0.0);
    double stdDev = Math.sqrt(recent.stream()
            .mapToDouble(m -> Math.pow(m.getValue() - mean, 2))
            .average().orElse(0.0));

    if (stdDev > 0 && Math.abs(newMetric.getValue() - mean) > 3 * stdDev) {
        Anomaly anomaly = new Anomaly(
                newMetric.getName(),
                newMetric.getValue(),
                newMetric.getTimestamp(),
                "Z-score anomaly detected (|x - μ| > 3σ)"
        );
        anomalyrepository.save(anomaly);
    }
    }

    

    public List<Metric> getLastMetrics() {
    return metricrepository.findTop10ByOrderByTimestampDesc();
    }
}