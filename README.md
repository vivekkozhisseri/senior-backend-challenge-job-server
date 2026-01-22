# Async Job Server

Asynchronous job processing server with Java 21, Spring Boot 3.3, and Hexagonal Architecture.

## Architecture

**Hexagonal Architecture (Ports & Adapters)** - The domain and application layers have zero framework dependencies, making them testable and portable.

```
                    ┌─────────────────────────────────────┐
                    │         INBOUND ADAPTERS            │
                    │         (REST Controller)           │
                    └─────────────────┬───────────────────┘
                                      │ calls
                    ┌─────────────────▼───────────────────┐
                    │         INBOUND PORTS               │
                    │         (JobUseCases)               │
                    └─────────────────┬───────────────────┘
                                      │ implements
┌─────────────────────────────────────▼───────────────────────────────────────┐
│                          APPLICATION LAYER                                  │
│                   JobService, JobProcessingService                          │
│                      (Pure Java - No Spring)                                │
│                                                                             │
│    ┌──────────────────────────────────────────────────────────────────┐    │
│    │                       DOMAIN LAYER                                │    │
│    │              Job, JobStatus, User, Project                        │    │
│    │                 (Pure Java - No Spring)                           │    │
│    └──────────────────────────────────────────────────────────────────┘    │
│                                                                             │
└─────────────────────────────────────┬───────────────────────────────────────┘
                                      │ calls
                    ┌─────────────────▼───────────────────┐
                    │        OUTBOUND PORTS               │
                    │  JobRepositoryPort                  │
                    │  ExternalProcessorPort              │
                    │  JobProcessorPort                   │
                    └─────────────────┬───────────────────┘
                                      │ implements
                    ┌─────────────────▼───────────────────┐
                    │        OUTBOUND ADAPTERS            │
                    │   JPA/PostgreSQL, WebClient/HTTP    │
                    │         @Async/ThreadPool           │
                    └─────────────────────────────────────┘
```

### Package Structure

| Package | Description |
|---------|-------------|
| `domain/` | Business entities and rules (no Spring dependencies) |
| `ports/inbound/` | Interfaces defining use cases (driving ports) |
| `ports/outbound/` | Interfaces for external dependencies (driven ports) |
| `application/` | Use case implementations |
| `adapters/inbound/rest/` | REST controllers, DTOs, exception handlers |
| `adapters/outbound/persistence/` | JPA entities, repositories, mappers |
| `adapters/outbound/external/` | HTTP client for external service |
| `adapters/outbound/async/` | Async job processing with thread pool |

## Run

```bash
cp .env.example .env
docker compose up --build
```

## Test

```bash
cd job-server && ./mvnw test
```

## API

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /jobs | Submit job |
| GET | /jobs/{id} | Get job status |

### OpenAPI
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs
- OpenAPI YAML: http://localhost:8080/v3/api-docs.yaml

### Submit Job
```bash
curl -X POST http://localhost:8080/jobs \
  -H "Content-Type: application/json" \
  -d '{"userId": "a1b2c3d4-e5f6-4a7b-8c9d-0e1f2a3b4c5d", "payload": "test"}'
```

### Sample Users/Projects
| Type | ID | Name |
|------|-----|------|
| User | a1b2c3d4-e5f6-4a7b-8c9d-0e1f2a3b4c5d | Alice |
| User | b2c3d4e5-f6a7-4b8c-9d0e-1f2a3b4c5d6e | John |
| Project | c3d4e5f6-a7b8-4c9d-0e1f-2a3b4c5d6e7f | Project Alpha |
| Project | d4e5f6a7-b8c9-4d0e-1f2a-3b4c5d6e7f8a | Project Beta |

## Design Notes

- **Hexagonal Architecture**: Domain has no Spring dependencies; testable in isolation
- **Async Processing**: `@Async` with configurable thread pool; request returns immediately
- **Failure Handling**: Errors caught and stored; job marked FAILED; other jobs unaffected

## Assumptions

- Users and projects pre-exist in database
- No authentication required
- Single instance deployment (no distributed processing)

## Known Issues

### Testcontainers + Docker Desktop 29.x

Integration tests using Testcontainers may fail with Docker Desktop 29.x due to an API compatibility issue.

**Error:** `client version 1.32 is too old. Minimum supported API version is 1.44`

**Official Issue:** https://github.com/testcontainers/testcontainers-java/issues/11419

**Workarounds:**

1. **Set API version for tests only** - Create `src/test/resources/docker-java.properties`:
   ```properties
   api.version=1.44
   ```

2. **Set globally** - Create `~/.docker-java.properties`:
   ```properties
   api.version=1.44
   ```

3. **CI/CD** - Use `docker:28-dind` instead of `docker:dind`

4. **Alternative** - Run integration tests via docker compose:
   ```bash
   docker compose up -d
   curl -X POST http://localhost:8080/jobs \
     -H "Content-Type: application/json" \
     -d '{"userId": "a1b2c3d4-e5f6-4a7b-8c9d-0e1f2a3b4c5d", "payload": "test"}'
   ```

Unit tests (`./mvnw test`) work without Docker.
