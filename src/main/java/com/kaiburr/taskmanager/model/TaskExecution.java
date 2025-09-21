package com.kaiburr.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Instant;

public class TaskExecution {
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Instant startTime;
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private Instant endTime;
  private String output;

  public TaskExecution() {}

  public TaskExecution(Instant startTime, Instant endTime, String output) {
    this.startTime = startTime;
    this.endTime = endTime;
    this.output = output;
  }

  public Instant getStartTime() { return startTime; }
  public void setStartTime(Instant startTime) { this.startTime = startTime; }
  public Instant getEndTime() { return endTime; }
  public void setEndTime(Instant endTime) { this.endTime = endTime; }
  public String getOutput() { return output; }
  public void setOutput(String output) { this.output = output; }
}
