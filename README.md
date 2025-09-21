# Anomaly API

A simple Spring Boot API for ingesting and retrieving system metrics.

---

## How to Run the App

### Prerequisites
- Java 21 (or newer)
- Maven
- PostgreSQL (running locally or via Docker)

### Run with Maven
cd C:\Users\Vaggelis\anomaly-api
.\mvnw.cmd spring-boot:run

By default, the app starts on:
http://localhost:8080


# Anomaly API

## Endpoints

### 1. POST metrics/ingest
Stores a metric data point.
**Headers :** Content-Type: application/json
**Request Body (JSON):**
```json
{
  "metricName": "cpu_usage",
  "value": 87.5,
  "timestamp": 1694505600000
}
Response:
{
  "id": 1,
  "metricName": "cpu_usage",
  "value": 87.5,
  "timestamp": "2025-09-15T10:15:30Z"
}
```
### 2. GET/metrics
Returns the latest metrics (e.g. last 10).
```json
Response:
[
  {
    "id": 1,
    "metricName": "cpu_usage",
    "value": 87.5,
    "timestamp": "2025-09-15T10:15:30Z"
  },
  {
    "id": 2,
    "metricName": "memory_usage",
    "value": 65.2,
    "timestamp": "2025-09-15T10:16:10Z"
  }
]
```
### 3.GET/health
Response:
```json
{
    "status": "OK"
}
```
### 4.GET/anomalies
Response:
```json
    {
        "id": 1,
        "metricName": "cpu_usage",
        "value": 200.0,
        "timestamp": "2025-09-19T16:51:14.922638Z",
        "reason": "Z-score anomaly detected (|x - μ| > 3σ)"
    }
```



# Database Schema
Postgres table:
```sql
CREATE TABLE metrics (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  value DOUBLE PRECISION NOT NULL,
  timestamp TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE anomalies (
    id BIGSERIAL PRIMARY KEY,
    metric_name VARCHAR(255) NOT NULL,
    value DOUBLE PRECISION NOT NULL,
    timestamp TIMESTAMPTZ NOT NULL DEFAULT now(),
    reason TEXT
);
```

# GIT Daily update
git status
git add .
git commit -m "Describe what you changed"
git push