package com.example.anomalyapi.model;

import lombok.Data;

@Data  // generates getters, setters, toString, equals, hashCode
public class MetricRequest {
    private String metricName;
    private Double value;
    private Long timestamp; // optional, if not sent we can fill with "now"
}