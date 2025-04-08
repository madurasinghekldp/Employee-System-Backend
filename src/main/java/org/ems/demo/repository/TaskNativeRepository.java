package org.ems.demo.repository;

import org.ems.demo.entity.TaskEntity;

import java.util.List;
import java.util.Map;

public interface TaskNativeRepository {

    List<TaskEntity> getAllTaskByEmployee(Long employeeId, int limit, int offset);

    List<TaskEntity> getAllTaskByUser(Long userId, int limit, int offset);

    Map<String, Integer> getTasksByStatus(Long companyId);

    Integer getTasksCountByUser(Long userId);

    Map<String, Integer> getTasksByStatusByUser(Long userId);

    Map<String, Integer> getEmployeeMonthlyRejectedTasks(Long employeeId);

    Map<String, Integer> getEmployeeMonthlyLateTasks(Long employeeId);
}
