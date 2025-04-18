package org.ems.demo.service;

import org.ems.demo.dto.Task;
import org.ems.demo.dto.TaskByUser;

import java.util.List;
import java.util.Map;

public interface TaskService {
    Task createTask(Task task,Long userId);

    List<Task> getAllTask(Long employeeId, int limit, int offset);
    List<Task> getAllTaskByUser(Long userId, int limit, int offset);

    Task updateTask(Task task);
    Task updateTaskByUser(TaskByUser task);

    void deleteTask(Long id);

    Map<String, Integer> getTaskByStatus(Long companyId);

    Integer getTaskCountsByUser(Long userId);

    Map<String, Integer> getTaskByStatusByUser(Long userId);

    Map<String,Integer> getEmployeeMonthlyRejectedTasks(Long employeeId);

    Map<String,Integer> getEmployeeMonthlyLateTasks(Long employeeId);
}
