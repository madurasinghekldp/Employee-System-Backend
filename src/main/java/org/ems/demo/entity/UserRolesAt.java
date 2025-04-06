package org.ems.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_roles_at")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRolesAt {

    @EmbeddedId
    private UserRolesAtId id;

    @ManyToOne
    @MapsId("userId") // maps userId part of the composite key
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @MapsId("roleId") // maps roleId part of the composite key
    @JoinColumn(name = "role_id")
    private UserRoleEntity role;
}

