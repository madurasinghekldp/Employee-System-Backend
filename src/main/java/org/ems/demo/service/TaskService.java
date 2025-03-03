package org.ems.demo.service;

import org.ems.demo.dto.Task;
import org.ems.demo.dto.TaskByUser;

import java.util.List;

public interface TaskService {
    Task createTask(Task task);

    List<Task> getAllTask(Long employeeId, int limit, int offset);
    List<Task> getAllTaskByUser(Integer userId, int limit, int offset);

    Task updateTask(Task task);
    Task updateTaskByUser(TaskByUser task);

    void deleteTask(Long id);
}
