package org.ems.demo.service;

public interface EmailService {
    void sendSimpleMessage(
            String to, String subject, String text);
}
