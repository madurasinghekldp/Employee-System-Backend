package org.ems.demo.repository;

import org.ems.demo.dto.Notification;
import org.ems.demo.entity.NotificationEntity;
import org.ems.demo.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificationRepository extends CrudRepository<NotificationEntity, Long> {
    List<NotificationEntity> findAllByToUserAndIsReadFalse(UserEntity userEntity);
}
