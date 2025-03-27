package org.dorixon.websiteproject.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.processing.Pattern;

import java.time.LocalDateTime;

@Entity
@Data
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)

    private String password;

    private boolean enabled = true;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;

    private LocalDateTime createdAt = LocalDateTime.now();
}