package com.academy.ccrpms.user.entity;

import com.academy.ccrpms.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private String email;

    @Column(name = "full_name")
    private String fullName;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    private boolean active = true;
}
