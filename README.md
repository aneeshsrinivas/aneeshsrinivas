<div align="center">

# Aneesh Srinivas
### Backend Engineer — Distributed Systems · REST APIs · Cloud Infrastructure

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0A66C2?style=flat-square&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/aneesh-srinivas-537b7a1b4/)
[![Email](https://img.shields.io/badge/Email-D14836?style=flat-square&logo=gmail&logoColor=white)](mailto:aneeshsrinivas@email.com)
[![LeetCode](https://img.shields.io/badge/LeetCode-FFA116?style=flat-square&logo=leetcode&logoColor=white)](https://leetcode.com/aneeshsrinivas)
[![GitHub](https://img.shields.io/badge/GitHub-181717?style=flat-square&logo=github&logoColor=white)](https://github.com/aneeshsrinivas)

</div>

---

I build backend systems that handle real load — job schedulers, multi-role platforms, and cloud-deployed APIs. My focus is on system design, fault tolerance, and writing backend code that works correctly under concurrent workloads.

Currently pursuing backend and full-stack engineering internships and entry-level roles.

---

## Engineering Specialization

```
Distributed Systems     Job scheduling, worker pools, concurrent processing, retry logic
Backend APIs            REST design, JWT auth, role-based access control, pagination
Data Engineering        Schema design, query optimization, PostgreSQL, MongoDB
Cloud & Deployment      Docker, GCP Cloud Run, AWS, GitHub Actions CI/CD
```

---

## Projects

### 1. Distributed Task Scheduler
`Java` `Spring Boot` `PostgreSQL` `Docker` `React`

**[Repository](https://github.com/aneeshsrinivas/distributed-task-scheduler)**

#### Problem

Cron jobs fail silently, have no concurrency control, no retry logic, and no observability. When a job fails at 3AM, nobody knows until business impact has already occurred. This project replaces that pattern with a proper scheduling engine built for correctness under concurrent load.

#### Architecture

```
Client (REST API)
      │
      ▼
  Job Submission Layer
  ┌──────────────────────────────────────┐
  │  POST /jobs → Validates payload,     │
  │  persists to DB with status=PENDING  │
  └─────────────────┬────────────────────┘
                    │
                    ▼
  Job Dispatcher (Polling thread)
  ┌──────────────────────────────────────┐
  │  Reads PENDING jobs, assigns to      │
  │  available workers via thread-safe   │
  │  bounded queue                       │
  └──────────┬──────────────────┬────────┘
             │                  │
             ▼                  ▼
         Worker-1   ...     Worker-N
         ┌───────┐           ┌───────┐
         │Execute│           │Execute│
         └───┬───┘           └───┬───┘
             │                  │
             ▼                  ▼
      Update status: SUCCESS / FAILED
             │
             ▼
  On failure → Dead-Letter Queue
  → Exponential backoff retry
  → Max retries exceeded → DEAD
             │
             ▼
  Audit log + Dashboard (GET /metrics)
```

#### Request Flow

1. Client submits job via `POST /api/jobs` with payload, schedule config, and priority
2. API validates and persists job with `status=PENDING`, `retry_count=0`
3. Dispatcher polls every N seconds for PENDING jobs ordered by priority and `scheduled_at`
4. Job claimed atomically via `SELECT FOR UPDATE SKIP LOCKED` — prevents double-processing across threads
5. Worker executes job, transitions status `RUNNING → SUCCESS` or `RUNNING → FAILED`
6. On failure: increments `retry_count`, calculates next retry via exponential backoff, re-queues
7. On `retry_count >= max_retries`: marks job `DEAD`, writes failure detail to audit log
8. Watchdog thread detects jobs stuck in `RUNNING` beyond timeout, resets to `PENDING`

#### Scaling Considerations

| Concern | Approach |
|---|---|
| Worker contention | `SELECT FOR UPDATE SKIP LOCKED` — workers never compete for the same job row |
| Throughput | Worker pool size is configurable per deployment; dispatcher can run as multiple instances |
| Database load | `jobs` table indexed on `(status, scheduled_at, priority)` — dispatcher query hits index only |
| Observability | Every state transition written to `job_audit_log` with timestamp and worker ID |

#### Failure Handling

- **Worker crash mid-job:** Watchdog detects `RUNNING` jobs beyond configurable timeout and resets them to `PENDING` for retry
- **Database unavailable:** Dispatcher catches connection exception, backs off with jitter, retries — process does not crash
- **Poison pill jobs:** Jobs exceeding max retries transition to `DEAD` and are excluded from polling — prevents queue starvation

#### Tradeoffs

- **Polling vs. event-driven:** Polling adds latency (configurable, default 5s). A message queue would reduce latency but adds operational overhead not justified at this scale. Interface is abstracted to allow future extraction.
- **In-process workers:** Workers run in the same JVM as the dispatcher for deployment simplicity. The worker abstraction is designed to be extracted into separate services if horizontal scaling of workers becomes necessary.

#### Measurable Impact

- Handles **1,000+ concurrent jobs** with no dropped tasks under load testing
- Retry logic reduced effective failure rate by ~40% versus a naive cron baseline
- Full audit trail on every job: status history, worker ID, retry count, failure reason

---

### 2. Chess Academy Management Platform
`Java` `Spring Boot` `PostgreSQL` `React` `Firebase` `Docker` `GCP Cloud Run`

**[Repository](https://github.com/aneeshsrinivas/chess-academy-platform)**

#### Problem

A chess academy managing 200+ students across multiple coaching batches was operating entirely on spreadsheets and WhatsApp. No single source of truth for enrollment or batch capacity. Scheduling conflicts were frequent. Fee tracking was manual and error-prone. The system needed to replace all of that with one platform across three user roles.

#### Architecture

```
                    ┌────────────────────────┐
                    │     React Frontend      │
                    │   Admin / Coach / User  │
                    └──────────┬─────────────┘
                               │ HTTPS / JWT
                               ▼
                    ┌────────────────────────┐
                    │    Spring Boot API      │
                    │  JWT Auth Middleware    │
                    │  @PreAuthorize per role │
                    └──────────┬─────────────┘
                               │
          ┌────────────────────┼─────────────────────┐
          ▼                    ▼                      ▼
┌──────────────────┐  ┌──────────────────┐  ┌─────────────────┐
│   PostgreSQL     │  │    Firebase       │  │  GCP Cloud Run  │
│  users, batches, │  │  Push notifs to  │  │  Dockerized,    │
│  enrollments,    │  │  coaches on new  │  │  auto-scales,   │
│  payments,       │  │  enrollment      │  │  scales to zero │
│  progress        │  └──────────────────┘  └─────────────────┘
└──────────────────┘
```

#### Request Flow — Enrollment

1. Student authenticates → JWT issued with role claim `STUDENT`
2. `GET /api/batches` → middleware validates JWT, returns available batches with remaining capacity
3. Student submits `POST /api/enrollments` with `batch_id`
4. Service layer opens transaction, acquires row lock on target batch (`SELECT ... FOR UPDATE`)
5. Checks `enrolled_count < capacity` — if false, returns `409 Conflict`
6. Writes enrollment record, increments `enrolled_count` atomically, commits transaction
7. Firebase notification dispatched async to assigned coach — non-blocking, does not affect response
8. `201 Created` returned to client

#### Database Schema

```sql
users        (id, name, email, password_hash, role, created_at)
batches      (id, name, coach_id, capacity, enrolled_count, schedule, level)
enrollments  (id, student_id, batch_id, status, enrolled_at)
             UNIQUE (student_id, batch_id)
sessions     (id, batch_id, date, notes, completed)
progress     (id, student_id, metric, value, recorded_at)
payments     (id, student_id, amount, status, due_date, paid_at)
```

#### Role-Based Access Control

| Role | Permissions |
|---|---|
| `ADMIN` | Full CRUD on users, batches, payments, reports |
| `COACH` | Read assigned batches; write session notes and student progress |
| `STUDENT` | Read own enrollment, sessions, progress; no write on batch data |

Role is embedded in the JWT claim at login. Every API endpoint is annotated with `@PreAuthorize` — access is denied at the framework level before business logic executes.

#### Failure Handling

- **Concurrent enrollment on last seat:** Row-level lock on batch ensures only one transaction can check and increment capacity at a time. Second request gets `409`.
- **Double enrollment:** `UNIQUE (student_id, batch_id)` constraint provides a database-level safety net even if application logic is bypassed.
- **Notification failure:** Firebase dispatch is async and does not block the enrollment transaction. Enrollment is not rolled back if notification fails.
- **Payment/enrollment mismatch:** Payment creation and enrollment status update are wrapped in a single transaction. Both commit or both roll back.

#### Tradeoffs

- **PostgreSQL over MongoDB:** Enrollment, payment, and user data has clear relational structure requiring multi-table transactions. Document storage would complicate consistency guarantees.
- **JWT over server sessions:** Stateless auth fits a Cloud Run deployment with no shared session store across container instances. Short token expiry mitigates the lack of server-side revocation.

#### Measurable Impact

- **200+ active users** across all three roles in production
- **30% reduction in manual administrative work** — enrollment, scheduling, and fee tracking fully automated
- Zero double-enrollment incidents after atomic capacity enforcement was implemented

---

## Diagrams to Build

Add these to each project's `/docs/diagrams/` directory. Reference them in the project README.

**Task Scheduler**
- Job state machine: `PENDING → RUNNING → SUCCESS / FAILED → DEAD`. Include the watchdog recovery edge from stuck `RUNNING` back to `PENDING`.
- Worker pipeline: Dispatcher poll → `SELECT FOR UPDATE SKIP LOCKED` → Worker thread assignment → Status update → Retry decision tree.
- Database ERD: `jobs` and `job_audit_log` tables with all columns and relationships.

**Chess Academy Platform**
- API auth flow: Request → JWT middleware → role check → controller → service → DB. Show 401 and 403 exit paths.
- Enrollment concurrency: Two simultaneous requests for the last seat — show transaction isolation, one success, one 409.
- Database ERD: All six core tables with foreign keys and cardinality.

Recommended tool: [Excalidraw](https://excalidraw.com) or [draw.io](https://draw.io). Export as PNG, commit to the repo, embed in README.

---

## Technical Writing

Publish on Medium or Dev.to. Link them in this README once live. Each topic demonstrates a depth of thinking that a GitHub repo alone cannot show.

1. **"Building a Concurrent Job Scheduler Without a Message Queue"** — `SELECT FOR UPDATE SKIP LOCKED`, worker pool design, polling vs. event-driven tradeoffs, and when simplicity beats Kafka.

2. **"Role-Based Access Control in Spring Boot That Actually Holds Under Pressure"** — JWT claims, `@PreAuthorize`, and why putting auth checks in business logic is a design mistake.

3. **"Handling Race Conditions in Enrollment Systems: A PostgreSQL Story"** — The double-enrollment problem, row-level locking, and unique constraints as a last line of defense.

4. **"PostgreSQL vs. MongoDB: A Decision Framework, Not a Tribal War"** — Structured tradeoff analysis for a real project with clear relational requirements.

5. **"Exponential Backoff, Dead-Letter Queues, and Making Failures Recoverable"** — Retry strategy design, poison pill detection, and how to build systems that fail gracefully.

---

## GitHub Analytics

<div align="center">

<img src="https://github-readme-stats.vercel.app/api?username=aneeshsrinivas&show_icons=true&theme=github_dark&hide_border=true&include_all_commits=true&count_private=true&rank_icon=github" height="160" />
<img src="https://github-readme-stats.vercel.app/api/top-langs/?username=aneeshsrinivas&layout=compact&theme=github_dark&hide_border=true&langs_count=6" height="160" />

<br/>

<img src="https://streak-stats.demolab.com?user=aneeshsrinivas&theme=github-dark-blue&hide_border=true" />

<br/>

<img src="https://github-readme-activity-graph.vercel.app/graph?username=aneeshsrinivas&theme=github-compact&hide_border=true&area=true&color=58A6FF&line=58A6FF&point=ffffff" width="100%" />

</div>

---

## Pinned Repository Order

| Position | Repository | What It Signals |
|---|---|---|
| 1 | `distributed-task-scheduler` | Systems thinking, concurrency, backend depth |
| 2 | `chess-academy-platform` | Full production system, real users, measurable outcomes |
| 3 | `dsa-solutions` | Problem-solving consistency, algorithmic range |
| 4 | Cloud / Docker deployment project | Deployment and DevOps competence |
| 5 | Any standalone API or microservice | API design and REST conventions |
| 6 | `aneeshsrinivas` (this profile repo) | Only if the README is strong — it is |

---

## Certifications

- Google Cloud AI — Certified
- Infosys Full-Stack Internship — Production deployment, internal tooling

---

<div align="center">

Available for backend engineering internships and entry-level positions.

**[aneeshsrinivas@email.com](mailto:aneeshsrinivas@email.com) · [LinkedIn](https://www.linkedin.com/in/aneesh-srinivas-537b7a1b4/) · [GitHub](https://github.com/aneeshsrinivas)**

</div>
