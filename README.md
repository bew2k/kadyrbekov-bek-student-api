# kadyrbekov-bek-student-api

Spring Boot CRUD application (Students) with PostgreSQL.

**Author:** Bek Kadyrbekov — group IT2-2306 — Student ID 35534

## Run

```bash
docker compose up -d --build
curl http://localhost:8081/api/info
```

## API

| Method | URL | Description |
|--------|-----|-------------|
| GET | /api/info | author and version |
| POST | /api/students | create student |
| GET | /api/students | list all |
| GET | /api/students/{id} | get one |
| PUT | /api/students/{id} | update |
| DELETE | /api/students/{id} | delete |

## Change log
- Bek Kadyrbekov: added `GET /api/students/search?surname=` (read operation improvement).
