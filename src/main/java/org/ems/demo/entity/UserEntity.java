package org.ems.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString//(exclude = {"userRoleEntities", "company"})
@Accessors(chain = true) // Enable chaining for setter methods
@Table(name = "users")
@Entity
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, length = 100, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles_at",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<UserRoleEntity> userRoleEntities;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonBackReference(value = "company-user")
    private CompanyEntity company;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @OneToMany(mappedBy = "approvedBy", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "user-task")
    private List<TaskEntity> tasks;

    @OneToMany(mappedBy = "approvedBy", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "user-leave")
    private List<LeaveEntity> leaves;

    @OneToMany(mappedBy = "fromUser", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "user-notification-from")
    private List<NotificationEntity> fromNotifications;

    @OneToMany(mappedBy = "toUser", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "user-notification-to")
    private List<NotificationEntity> toNotifications;

    private boolean isActive = true;

    @Column(name = "profile_image")
    private String profileImage;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRoleEntities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
