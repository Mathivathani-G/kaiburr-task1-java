# Kaiburr — Task 1: Java REST API (Task Manager)

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
List:
```bash
curl http://localhost:8080/api/tasks
```
Get by id:
```bash
curl http://localhost:8080/api/tasks/t1
```
Search:
```bash
curl "http://localhost:8080/api/tasks/search?name=Date"
```
Execute:
```bash
curl -X PUT http://localhost:8080/api/tasks/t1/execute
```
Delete:
```bash
curl -i -X DELETE http://localhost:8080/api/tasks/t1
```
