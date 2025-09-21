package com.kaiburr.taskmanager.util;

import java.util.Set;
import java.util.regex.Pattern;

public class CommandValidator {
  private static final Set<String> ALLOWED = Set.of(
    "echo", "date", "uname", "whoami", "ls", "pwd", "cat", "id", "uptime"
  );

  private static final Pattern FORBIDDEN = Pattern.compile("[;&|`$<>\\\\]");

  public static boolean isValid(String command) {
    if (command == null || command.isBlank()) return false;
    if (FORBIDDEN.matcher(command).find()) return false;
    String[] parts = command.trim().split("\\s+");
    return parts.length >= 1 && ALLOWED.contains(parts[0]);
  }
}
