package org.ems.demo.repository;

import org.ems.demo.entity.NotificationEntity;
import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends CrudRepository<NotificationEntity, Long> {
}
