package com.example.anomalyapi.repository;

import com.example.anomalyapi.model.Anomaly;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.time.Instant;
import java.util.List;

public interface AnomalyRepository extends JpaRepository<Anomaly, Long> {

    List<Anomaly> findByMetricNameAndTimestampBetween(
            String metricName,
            Instant from,
            Instant to,
            Pageable pageable
    );

    List<Anomaly> findByMetricName(String metricName,Pageable pageable); // optional, for cases without range
    
    List<Anomaly> findAllBy(Pageable pageable);
}