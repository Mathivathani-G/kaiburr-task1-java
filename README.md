# Kaiburr - Task 1: Java REST API (Task Manager)
# Kaiburr - Task 4: CI-CD pipeline for JAVA REST API Backend

**Name**: Mathivathani G

**Date**: 21-09-2025  

---

## Overview
This repository contains my submission for **Task 1** of the Kaiburr assessment.  
I have implemented a **Java Spring Boot REST API** that connects to **MongoDB** and manages `Task` objects.  

The API supports the following operations:
- Create or update a Task  
- List all Tasks  
- Get a Task by its ID  
- Search for Tasks by name  
- Delete a Task  
- Execute a Task’s command and record the output with start/end times  

---

##  Tech Stack
- **Java 17** (Eclipse Temurin)  
- **Spring Boot 3**  
- **MongoDB 6.0 (Dockerized)**  
- **Maven** (for build management)  
- **curl** (for API testing, Postman can also be used)  
- **macOS (M1 Pro)** as development environment  

---

##How to Run

### 1. Start MongoDB (in Docker)
```bash
docker run -d --name kaiburr-mongo -p 27017:27017 -v kaiburr_mongo_data:/data/db mongo:6.0
```
Build app:
```bash
mvn clean package -DskipTests
```

Run app:
```bash
java -Dspring.data.mongodb.uri=mongodb://localhost:27017/kaiburr -jar target/taskmanager-0.0.1-SNAPSHOT.jar
```
API base: http://localhost:8080/api/tasks

API Examples (curl)
Create task:
```bash
curl -X PUT http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"id":"t1","name":"Check Date","owner":"Mathivathani","command":"date"}'
```
**Task Created**


![TASK CREATED](https://github.com/Mathivathani-G/kaiburr-task1-java/blob/main/screenshots/screenshots/JSON-Task%20Created.png)
Execute:
```bash
curl -X PUT http://localhost:8080/api/tasks/t1/execute
```
**Task Executed**


![TASK EXECUTE](https://github.com/Mathivathani-G/kaiburr-task1-java/blob/main/screenshots/screenshots/Execute%20Task.jpeg)

List:
```bash
curl http://localhost:8080/api/tasks
```
**Listing available Tasks (t1,t2)**


![List task](https://github.com/Mathivathani-G/kaiburr-task1-java/blob/main/screenshots/screenshots/LIST%20all%20Task.jpeg)
Get by id:
```bash
curl http://localhost:8080/api/tasks/t1
```
**GET task by ID**


![Get Task By ID](https://github.com/Mathivathani-G/kaiburr-task1-java/blob/main/screenshots/screenshots/GET%20task%20by%20ID.jpeg)

Search:
```bash
curl "http://localhost:8080/api/tasks/search?name=Date"
```
**SEARCH task by name**


![Search by name](https://github.com/Mathivathani-G/kaiburr-task1-java/blob/main/screenshots/screenshots/Search%20by%20name.jpeg)
Delete:
```bash
curl -i -X DELETE http://localhost:8080/api/tasks/t1
```
**TASK t1 Deleted**


![Task delete](https://github.com/Mathivathani-G/kaiburr-task1-java/blob/main/screenshots/screenshots/DELETE%20task.jpeg)


**MONGO DB Container**


![MongoDB Container](https://github.com/Mathivathani-G/kaiburr-task1-java/blob/main/screenshots/screenshots/Mongo%20Container%20Running.png)

**MongoDB Output**


![MongoDB Container](https://github.com/Mathivathani-G/kaiburr-task1-java/blob/main/screenshots/screenshots/check%20in%20Mongo.png)







---

# **Task 4 - CI-CD pipeline for Backend (Java Spring Boot)**
## CI/CD Pipeline - Backend

This project uses **GitHub Actions** to implement a fully automated **CI/CD pipeline** for the Java backend.

### Workflow

1. **Trigger**: Workflow runs on every `push` or `pull request` to the `main` branch.
2. **Steps**:
   - **Checkout code** from GitHub.
   - **Build Java project** using Maven (`mvn clean package`) → produces `.jar`.
   - **Build Docker image** using the provided `Dockerfile`.
   - **Run Docker container** to verify the backend API.
3. **Dockerfile**:
   - Uses `openjdk:17` base image.
   - Copies the built `.jar` file.
   - Exposes port `8080`.

### Outcome

- Docker image for backend is built automatically on every push.
- Workflow status visible in GitHub Actions.
- Backend app can be run locally using:

```bash
docker build -t kaiburr-backend:latest .
docker run -p 8080:8080 kaiburr-backend:latest
