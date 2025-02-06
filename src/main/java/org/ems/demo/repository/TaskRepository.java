package org.ems.demo.repository;

import org.ems.demo.entity.TaskEntity;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<TaskEntity,Long> {
}
