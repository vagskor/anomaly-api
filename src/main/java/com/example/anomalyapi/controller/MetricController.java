package com.example.anomalyapi.controller;

import com.example.anomalyapi.model.Metric;
import com.example.anomalyapi.model.MetricRequest;
import com.example.anomalyapi.service.MetricService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/metrics")
public class MetricController {

    private final MetricService service;

    public MetricController(MetricService service) {
        this.service = service;
    }

    // POST /metrics/ingest 
    @PostMapping("/ingest")
    public Metric ingest(@RequestBody MetricRequest request) {
        return service.saveMetric(request);
    }

    // GET /metrics → fetch all metrics
    @GetMapping
    public List<Metric> getLastMetrics() {
        return service.getLastMetrics();
    }
}