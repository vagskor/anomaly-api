package com.example.anomalyapi.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "metrics")
public class Metric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // tells JPA to use DB auto-increment
    private Long id;

    @Column(name = "metric_name", nullable = false)
    private String name;

    @Column(nullable = false)
    private Double value;

    @Column(nullable = false)
    private Instant timestamp = Instant.now(); // default to current time

    // Constructors
    public Metric() {}

    public Metric(Long id, String name, Double value) {
        this.id = id;       // usually null when creating new, DB will auto-generate
        this.name = name;
        this.value = value;
        this.timestamp = Instant.now();
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
