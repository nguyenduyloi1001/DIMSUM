package com.example.DIMSUM.model;

import com.example.DIMSUM.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String fullName;

    private String phone;

    @Column(nullable = false)
    private String email;


    @Enumerated(EnumType.STRING) //lưu enum xuống bằng chữ
    @Column(nullable = false)
    private Role role;

}
