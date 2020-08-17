package com.kira.micro.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="users")
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, length = 50)
    private String firstName;
    @Column(nullable = false, length = 50)
    private String lastName;
    @Column(nullable = false, length = 50, unique = true)
    private String emailId;
    @Column(nullable = false, unique = true)
    private String encryptedPassword;
    @Column(nullable = false, unique = true)
    private String userId;
}
