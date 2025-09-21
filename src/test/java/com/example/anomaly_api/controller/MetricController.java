package com.example.anomaly_api.controller;

import com.example.anomalyapi.model.Metric;
import com.example.anomalyapi.model.MetricRequest;
import com.example.anomaly_api.service.MetricService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/metrics")
public class MetricController {

    private final MetricService metricService;

    public MetricController(MetricService metricService) {
        this.metricService = metricService;
    }

    @PostMapping
    public Metric createMetric(@RequestBody MetricRequest request) {
        return metricService.createMetric(request);
    }

    @GetMapping
    public List<Metric> getAllMetrics() {
        return metricService.getAllMetrics();
    }
}