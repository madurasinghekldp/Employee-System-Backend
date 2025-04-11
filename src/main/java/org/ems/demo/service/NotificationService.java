package org.ems.demo.service;

import org.ems.demo.dto.Notification;

import java.util.List;

public interface NotificationService {
    List<Notification> getAllNotifications(Long userId);

    void markAsRead(Long notifyId);
}
