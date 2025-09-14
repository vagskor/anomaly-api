# Anomaly API

A simple Spring Boot API for ingesting and retrieving system metrics.

---

## How to Run the App

### Prerequisites
- Java 21 (or newer)
- Maven
- PostgreSQL (running locally or via Docker)

### Run with Maven
.\mvnw.cmd spring-boot:run

By default, the app starts on:
http://localhost:8080

API Endpoints
Health Check
curl http://localhost:8080/health
Response:
OK

Ingest Metric
curl -X POST http://localhost:8080/ingest \
  -H "Content-Type: application/json" \
  -d '{"name":"cpu_usage","value":87.5}'
Response:
{
  "id": 1,
  "name": "cpu_usage",
  "value": 87.5,
  "timestamp": "2025-09-12T19:24:02.005481Z"
}

Fetch Last 10 Metrics
curl http://localhost:8080/metrics
Response:
[
  {
    "id": 35,
    "name": "cpu_usage",
    "value": 87.5,
    "timestamp": "2025-09-12T17:37:55.774662Z"
  },
  {
    "id": 34,
    "name": "memory_usage",
    "value": 65.2,
    "timestamp": "2025-09-12T17:37:13.950411Z"
  }
]

Database Schema
Postgres table:
CREATE TABLE metrics (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  value DOUBLE PRECISION NOT NULL,
  timestamp TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);