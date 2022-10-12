package com.blog.demo.auth.entity;

import com.blog.demo.auth.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class RolEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Roles roles;

    public RolEntity() {

    }

    public RolEntity(@NotNull Roles roles) {
        this.roles = roles;
    }
}