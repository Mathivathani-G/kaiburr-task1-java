package com.kaiburr.taskmanager.controller;

import com.kaiburr.taskmanager.model.Task;
import com.kaiburr.taskmanager.model.TaskExecution;
import com.kaiburr.taskmanager.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@Validated
public class TaskController {
  private final TaskService service;
  public TaskController(TaskService service) { this.service = service; }

  @GetMapping
  public ResponseEntity<List<Task>> listAll() {
    return ResponseEntity.ok(service.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Task> getById(@PathVariable String id) {
    return service.getById(id)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @PutMapping
  public ResponseEntity<?> putTask(@RequestBody Task task) {
    try {
      Task saved = service.save(task);
      return ResponseEntity.ok(saved);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable String id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/search")
  public ResponseEntity<List<Task>> search(@RequestParam("name") String name) {
    var list = service.searchByName(name);
    if (list.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    return ResponseEntity.ok(list);
  }

  @PutMapping("/{id}/execute")
  public ResponseEntity<?> execute(@PathVariable String id) {
    try {
      TaskExecution exec = service.executeTask(id);
      return ResponseEntity.ok(exec);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Execution failed: " + e.getMessage());
    }
  }
}
