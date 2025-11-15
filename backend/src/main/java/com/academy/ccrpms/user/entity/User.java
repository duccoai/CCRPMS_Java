package com.academy.ccrpms.user.entity;

import com.academy.ccrpms.common.BaseEntity;
import com.academy.ccrpms.job.entity.Job;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    private boolean active = true;

    // Thêm các trường phụ trợ
    private String avatarUrl; // link ảnh đại diện
    private String bio;       // mô tả ngắn
    private String cvUrl;     // link CV của user

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    // Nếu user là recruiter, liên kết với Job
    @OneToMany(mappedBy = "recruiter", cascade = CascadeType.ALL)
    private List<Job> jobs;
}
