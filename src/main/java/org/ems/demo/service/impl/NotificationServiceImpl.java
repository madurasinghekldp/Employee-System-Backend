package org.ems.demo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ems.demo.dto.Notification;
import org.ems.demo.entity.NotificationEntity;
import org.ems.demo.entity.UserEntity;
import org.ems.demo.exception.NotificationException;
import org.ems.demo.repository.NotificationRepository;
import org.ems.demo.repository.UserRepository;
import org.ems.demo.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final ObjectMapper mapper;
    @Override
    public List<Notification> getAllNotifications(Long userId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new NotificationException("User not found"));
        List<NotificationEntity> allByToUser = notificationRepository.findAllByToUserAndIsReadFalse(userEntity);
        List<Notification> notifications = new ArrayList<>();
        allByToUser.forEach(notification->{
            notifications.add(mapper.convertValue(notification, Notification.class));
        });
        return notifications;
    }

    @Override
    public void markAsRead(Long notifyId) {
        NotificationEntity notificationEntity = notificationRepository.findById(notifyId).orElseThrow(() -> new NotificationException("Notification not found"));
        notificationEntity.setRead(true);
        notificationRepository.save(notificationEntity);
    }
}
