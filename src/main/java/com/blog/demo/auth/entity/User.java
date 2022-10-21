package com.blog.demo.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @NotNull
    @Column(unique = true)
    private String userName;

    @Column
    @NotNull
    private String password;

    private String tokenPassword;

    @Column(unique = true)
    @Email
    @NotNull
    private String email;

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_rol", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<RolEntity> roles = new HashSet<>();

    public User( @NotNull String userName,  @NotNull String email, @NotNull String password){

        this.userName = userName;
        this.email = email;
        this.password = password;
    }
}
