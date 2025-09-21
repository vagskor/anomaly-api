package com.example.anomalyapi.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "anomalies")
public class Anomaly {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String metricName;
    private Double value;
    private Instant timestamp;
    private String reason;

    public Anomaly() {}

    public Anomaly(String metricName, Double value, Instant timestamp, String reason) {
        this.metricName = metricName;
        this.value = value;
        this.timestamp = timestamp;
        this.reason = reason;
    }

    // Getters and setters (or use Lombok @Data)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMetricName() { return metricName; }
    public void setMetricName(String metricName) { this.metricName = metricName; }

    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }

    public String getReason() { return reason; }
    public void setReason(String reason) {this.reason=reason;}
}
