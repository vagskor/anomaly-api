package com.example.anomalyapi.service;

import com.example.anomalyapi.model.Anomaly;




import java.time.Instant;
import java.util.List;

public class  AnomalyDetector {


    public static Anomaly detect(
        String metricName,
        Double newValue,
        Instant timestamp,
        List<Double> pastValues,
        int windowSize, 
        double threshold
    ) {if (pastValues.isEmpty()) {
            return null; // no baseline yet
        }
        
         // Take only the last N values (window)
        int start = Math.max(0, pastValues.size() - windowSize);
        List<Double> window = pastValues.subList(start, pastValues.size());

        double mean = window.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        double variance = window.stream().mapToDouble(v -> Math.pow(v - mean, 2)).average().orElse(0.0);
        double stdDev = Math.sqrt(variance);

        if (stdDev == 0) {
            return null; // no variability → cannot detect anomaly
        }

        double zScore = Math.abs((newValue - mean) / stdDev);

        if (zScore > threshold) {
            String reason = String.format("Value %.2f is %.2f std devs from mean %.2f", newValue, zScore, mean);
            return new Anomaly(metricName, newValue, timestamp, reason);
        }

        return null; // not an anomaly
    }
}

