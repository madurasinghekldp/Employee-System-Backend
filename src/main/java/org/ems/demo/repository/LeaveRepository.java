package org.ems.demo.repository;

import org.ems.demo.entity.LeaveEntity;
import org.springframework.data.repository.CrudRepository;

public interface LeaveRepository extends CrudRepository<LeaveEntity,Long> {
}
