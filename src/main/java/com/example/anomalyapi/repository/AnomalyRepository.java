package com.example.anomalyapi.repository;

import com.example.anomalyapi.model.Anomaly;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnomalyRepository extends JpaRepository<Anomaly, Long> {
}