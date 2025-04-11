package org.ems.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Accessors(chain = true)
@Table(name="notification")
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_user")
    @JsonBackReference(value = "user-notification-from")
    private UserEntity fromUser;

    @ManyToOne
    @JoinColumn(name = "to_user")
    @JsonBackReference(value = "user-notification-to")
    private UserEntity toUser;

    private String message;

    private boolean isRead = false;
}
