package com.example.anomalyapi.service;

import com.example.anomalyapi.model.Metric;
import com.example.anomalyapi.model.MetricRequest;
import com.example.anomalyapi.repository.MetricRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetricService {

    private final MetricRepository repo;

    public MetricService(MetricRepository repo) {
        this.repo = repo;
    }

    public Metric createMetric(MetricRequest request) {
        Metric metric = new Metric();
        metric.setName(request.getName());
        metric.setValue(request.getValue());
        metric.setTimestamp(java.time.Instant.now()); // set current timestamp
        return repo.save(metric);
    }

    public List<Metric> getLast10Metrics() {
        return repo.findTop10ByOrderByTimestampDesc();
    }

}