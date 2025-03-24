
# 🎬 Popcorn Palace By Nadia Safadi – Backend Setup & Usage Guide

Welcome to the backend service for **Popcorn Palace**, a movie ticket booking system built with **Java Spring Boot** and **PostgreSQL**. This guide will help you set up, run, and test the application.

---

## ✅ Prerequisites

Please ensure the following are installed:

| Tool      | Version | Link                                      |
|-----------|---------|-------------------------------------------|
| Java JDK  | 21+     | https://www.oracle.com/java/technologies/downloads/#java21 |
| Maven     | 3.8+    | https://maven.apache.org/download.cgi     |
| Docker    | Latest  | https://www.docker.com/products/docker-desktop |
| IDE       | Any     | IntelliJ IDEA (Recommended) or VS Code    |

---

## 🚀 Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/safadinadia21/popcorn-palace.git
cd popcorn-palace
```
---

### 2. Start PostgreSQL Using Docker

This project uses Docker for easy PostgreSQL setup. Start the database container with:

```bash
docker compose up -d
```

This will:
- Start a PostgreSQL instance on port `5432`
- Use the credentials:
  - Username: `popcorn-palace`
  - Password: `popcorn-palace`
  - Database: `popcorn-palace`

---

### 3. Build the Application

Use Maven to build the project:

```bash
mvn clean install
```

---

### 4. Run the Spring Boot App

```bash
mvn spring-boot:run
```

Once started, the API will be available at:  
👉 **http://localhost:8080**

---

## 🔌 API Endpoints Overview

### 🎬 Movie API
| Method | Endpoint                 | Description         |
|--------|--------------------------|---------------------|
| GET    | `/movies/all`            | Get all movies      |
| POST   | `/movies`                | Add a movie         |
| POST   | `/movies/update/{title}`| Update a movie      |
| DELETE | `/movies/{title}`       | Delete a movie      |

### ⏰ Showtime API
| Method | Endpoint                    | Description            |
|--------|-----------------------------|------------------------|
| GET    | `/showtimes/{id}`           | Get showtime by ID     |
| POST   | `/showtimes`                | Add a showtime         |
| POST   | `/showtimes/update/{id}`    | Update a showtime      |
| DELETE | `/showtimes/{id}`           | Delete a showtime      |

### 🎟️ Booking API
| Method | Endpoint     | Description       |
|--------|--------------|-------------------|
| POST   | `/bookings`  | Book a ticket     |

---
## 🧪 Running Tests

To execute all unit and integration tests:

```bash
mvn test
```

Test coverage includes:
- Controllers
- Services
- Repositories
- Exception handling

---

## 🛑 Clean Up

To stop the PostgreSQL container:

```bash
docker compose down
```

---

## 📁 Files to Check

- `src/main/resources/application.yaml` – Spring + DB config  
- `compose.yml` – Docker PostgreSQL setup  
- `README.md` – API overview  
- `Instructions.md` – This file  

---
