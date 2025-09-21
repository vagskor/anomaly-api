package com.example.anomaly_api.service;

import com.example.anomalyapi.model.Metric;
import com.example.anomalyapi.model.MetricRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MetricService {

    private final List<Metric> metrics = new ArrayList<>();
    private long nextId = 1;

    public Metric createMetric(MetricRequest request) {
        Metric metric = new Metric(nextId++, request.getMetricName(), request.getValue());
        metrics.add(metric);
        return metric;
    }

    public List<Metric> getAllMetrics() {
        return metrics;
    }
    
}