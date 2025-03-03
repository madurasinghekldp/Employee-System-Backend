package org.ems.demo.repository;

import org.ems.demo.entity.TaskEntity;

import java.util.List;

public interface TaskNativeRepository {

    List<TaskEntity> getAllTaskByEmployee(Long employeeId, int limit, int offset);

    List<TaskEntity> getAllTaskByUser(Integer userId, int limit, int offset);
}
