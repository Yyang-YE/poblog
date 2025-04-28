package com.project.poblog.domain.user.entity;

import com.project.poblog.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private Role role;

    @Builder(access = AccessLevel.PRIVATE)
    public User(String email, String password, String name, String nickname, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.role = role;
    }

    public static User of(String email, String password, String name, String nickname, Role role) {
        return User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .name(name)
                .role(role)
                .build();
    }


}
