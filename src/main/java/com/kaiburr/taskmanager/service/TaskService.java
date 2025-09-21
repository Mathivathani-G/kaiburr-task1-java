package com.kaiburr.taskmanager.service;

import com.kaiburr.taskmanager.model.Task;
import com.kaiburr.taskmanager.model.TaskExecution;
import com.kaiburr.taskmanager.repository.TaskRepository;
import com.kaiburr.taskmanager.util.CommandValidator;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class TaskService {
  private final TaskRepository repo;

  public TaskService(TaskRepository repo) {
    this.repo = repo;
  }

  public List<Task> getAll() {
    return repo.findAll();
  }

  public Optional<Task> getById(String id) {
    return repo.findById(id);
  }

  public Task save(Task t) {
    if (!CommandValidator.isValid(t.getCommand())) {
      throw new IllegalArgumentException("Command not allowed or unsafe.");
    }
    return repo.save(t);
  }

  public void delete(String id) {
    repo.deleteById(id);
  }

  public List<Task> searchByName(String namePart) {
    return repo.findByNameContainingIgnoreCase(namePart);
  }

  public TaskExecution executeTask(String id) throws Exception {
    Task task = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found: " + id));
    String command = task.getCommand();

    if (!CommandValidator.isValid(command)) {
      throw new IllegalArgumentException("Command not allowed or unsafe.");
    }

    Instant start = Instant.now();
    ProcessBuilder pb = new ProcessBuilder("/bin/sh", "-c", command);
    pb.redirectErrorStream(true);
    Process p = pb.start();

    StringBuilder out = new StringBuilder();
    try (BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
      String line;
      while ((line = in.readLine()) != null) {
        out.append(line).append("\n");
      }
    }

    boolean finished = p.waitFor(30, TimeUnit.SECONDS);
    if (!finished) {
      p.destroyForcibly();
      out.append("\n[PROCESS TIMED OUT]");
    }

    Instant end = Instant.now();

    TaskExecution execution = new TaskExecution(start, end, out.toString().trim());
    task.getTaskExecutions().add(execution);
    repo.save(task);
    return execution;
  }
}
