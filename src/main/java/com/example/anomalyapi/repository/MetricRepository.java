package com.example.anomalyapi.repository;


import com.example.anomalyapi.model.Metric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MetricRepository extends JpaRepository<Metric, Long> {
    // Spring Data parses this method name and generates the query automatically
    List<Metric> findTop10ByOrderByTimestampDesc();
}
