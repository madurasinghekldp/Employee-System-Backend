package org.ems.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    private Long id;
    private User fromUser;
    private User toUser;
    private String message;
    private boolean isRead = false;
}
