package com.KaizakiSoftwares.JobPortal.role;

import com.KaizakiSoftwares.JobPortal.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

/**
 * This Is the Model class for role
 * In role class, The name is unique identifier of the role
 * "@EntityListeners(AuditingEntityListener.class)" is used to enable auditing for the role entity
 * if you use it, remember to enable @JpaAuditing in the main class
 * "@JsonIgnore" is used to ignore the users field in the role class when it is serialized to JSON
 * prevent infinite recursion when serializing the user object
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Role {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<User> users;

    @CreatedDate
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdDate;
    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;
}
