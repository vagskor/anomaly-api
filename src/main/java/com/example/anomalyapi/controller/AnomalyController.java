package com.example.anomalyapi.controller;

import com.example.anomalyapi.model.Anomaly;
import com.example.anomalyapi.repository.AnomalyRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AnomalyController {

    private final AnomalyRepository repository;

    public AnomalyController(AnomalyRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/anomalies")
    public List<Anomaly> getAnomalies() {
        return repository.findAll();
    }
}
