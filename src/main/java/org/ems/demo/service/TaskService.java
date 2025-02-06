package org.ems.demo.service;

import org.ems.demo.dto.Task;

import java.util.List;

public interface TaskService {
    Task createTask(Task task);

    List<Task> getAllTask(Long employeeId, int limit, int offset);

    Task updateTask(Task task);

    void deleteTask(Long id);
}
