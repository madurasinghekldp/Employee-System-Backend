package org.ems.demo.repository;

import org.ems.demo.entity.TaskEntity;

import java.util.List;
import java.util.Map;

public interface TaskNativeRepository {

    List<TaskEntity> getAllTaskByEmployee(Long employeeId, int limit, int offset);

    List<TaskEntity> getAllTaskByUser(Integer userId, int limit, int offset);

    Map<String, Integer> getTasksByStatus(Long companyId);

    Integer getTasksCountByUser(Integer userId);

    Map<String, Integer> getTasksByStatusByUser(Integer userId);
}
