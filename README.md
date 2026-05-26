# Onwelo Recruitment Task — Voting System

REST API for a voting system built with Spring Boot. Allows registering voters and elections, then casting votes with enforced business rules.

## Tech Stack

- **Java 21**
- **Spring Boot 4.0.6**
- **PostgreSQL 17** (via Docker)
- **Flyway** — database migrations
- **Hibernate / Spring Data JPA**
- **Lombok**

## Architecture

The application follows **Hexagonal Architecture (Ports & Adapters)** organized as a modular monolith with three independent modules:

| Module | Responsibility |
|--------|---------------|
| `voter` | Registering and blocking voters |
| `election` | Creating elections and their options |
| `voting` | Casting votes with business rule validation |

Cross-module communication goes through facades (`VoterFacade`, `ElectionFacade`), keeping modules decoupled at the code level.

Voting business rules are implemented via the **Policy / Composite** pattern — each rule is a separate `@Component` validated in sequence before a vote is persisted.

## Running the Application

### Prerequisites

- Docker
- Java 21

### Start

```bash
docker compose up -d
./gradlew bootRun
```

The application starts on **port 8080**. Flyway runs migrations automatically on startup.

### Run Tests

```bash
./gradlew test
```

## Environment Variables

| Variable | Default | Description |
|----------|---------|-------------|
| `DB_URL` | `jdbc:postgresql://localhost:5433/onwelo` | JDBC datasource URL |
| `POSTGRES_USERNAME` | `onwelo` | Database username |
| `POSTGRES_PASSWORD` | `onwelo` | Database password |

---

## API Reference

All responses use `application/json`. Errors follow [RFC 9457 Problem Details](https://www.rfc-editor.org/rfc/rfc9457).

### Voters

#### Register a voter

```http
POST /api/voters
```

**Request body**
```json
{
  "firstName": "Jan",
  "lastName": "Kowalski",
  "email": "jan.kowalski@example.com"
}
```

**Response** `201 Created`
```json
{
  "id": "a1b2c3d4-...",
  "firstName": "Jan",
  "lastName": "Kowalski",
  "email": "jan.kowalski@example.com",
  "isBlocked": false
}
```

| Status | Reason |
|--------|--------|
| `409 Conflict` | Voter with this email already exists |

---

#### Block a voter

```http
PATCH /api/voters/{id}/block
```

**Response** `204 No Content`

| Status | Reason |
|--------|--------|
| `404 Not Found` | Voter not found |

---

#### Unblock a voter

```http
PATCH /api/voters/{id}/unblock
```

**Response** `204 No Content`

| Status | Reason |
|--------|--------|
| `404 Not Found` | Voter not found |

---

### Elections

#### Create an election

```http
POST /api/elections
```

**Request body**
```json
{
  "name": "Presidential Election 2026",
  "description": "Annual presidential election",
  "options": [
    { "name": "Candidate A", "description": "Party X" },
    { "name": "Candidate B", "description": "Party Y" }
  ]
}
```

> `description` is optional. `options` can be empty — options can be added later.

**Response** `201 Created`
```json
{
  "id": "e1f2g3h4-...",
  "name": "Presidential Election 2026",
  "description": "Annual presidential election",
  "options": [
    { "id": "o1o2o3o4-...", "name": "Candidate A", "description": "Party X" },
    { "id": "o5o6o7o8-...", "name": "Candidate B", "description": "Party Y" }
  ]
}
```

| Status | Reason |
|--------|--------|
| `409 Conflict` | Election with this name already exists |

---

#### Add an option to an election

```http
POST /api/elections/{id}/options
```

**Request body**
```json
{
  "name": "Candidate C",
  "description": "Party Z"
}
```

**Response** `201 Created` — full election object (same as above)

| Status | Reason |
|--------|--------|
| `404 Not Found` | Election not found |
| `409 Conflict` | Option with this name already exists in this election |

---

#### Get an election

```http
GET /api/elections/{id}
```

**Response** `200 OK` — full election object (same as above)

| Status | Reason |
|--------|--------|
| `404 Not Found` | Election not found |

---

### Votes

#### Cast a vote

```http
POST /api/votes
```

**Request body**
```json
{
  "voterId": "a1b2c3d4-...",
  "electionId": "e1f2g3h4-...",
  "optionId": "o1o2o3o4-..."
}
```

**Response** `201 Created`
```json
{
  "id": "v1v2v3v4-...",
  "voterId": "a1b2c3d4-...",
  "electionId": "e1f2g3h4-...",
  "optionId": "o1o2o3o4-...",
  "castAt": "2026-05-26T18:00:00Z"
}
```

| Status | Reason |
|--------|--------|
| `400 Bad Request` | Voter is blocked or selected option does not belong to this election |
| `404 Not Found` | Election does not exist |
| `409 Conflict` | Voter has already voted in this election |

---

## Business Rules

Voting validates the following rules in order — first failure throws immediately (fail-fast):

1. **Voter eligibility** — voter must exist and not be blocked
2. **Election existence** — election with given ID must exist
3. **Valid option** — option must belong to the specified election
4. **No duplicate vote** — voter can cast only one vote per election (but can vote in multiple elections)
