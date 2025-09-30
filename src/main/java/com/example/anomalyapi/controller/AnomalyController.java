package com.example.anomalyapi.controller;

import com.example.anomalyapi.model.Anomaly;
import com.example.anomalyapi.repository.AnomalyRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.data.domain.PageRequest;


import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/anomalies")
public class AnomalyController {

    private final AnomalyRepository repository;

    public AnomalyController(AnomalyRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Anomaly> getAnomalies(

        @RequestParam(required = false) String metricName,
        @RequestParam(required = false) Long from,
        @RequestParam(required = false) Long to,
        @RequestParam(defaultValue = "20") int limit) {
            PageRequest page = PageRequest.of(0, limit);

            if (metricName != null && from != null && to != null) {
            Instant fromInstant = Instant.ofEpochSecond(from);
            Instant toInstant   = Instant.ofEpochSecond(to);
            return repository.findByMetricNameAndTimestampBetween(metricName, fromInstant, toInstant, page );
            }   
            else if (metricName != null) {
            return repository.findByMetricName(metricName,page);
            } 
            else {
            return repository.findAllBy(page);
            }
    }
       
}
