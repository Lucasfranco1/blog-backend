package com.blog.demo.auth.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserDTO {
    @NotNull
    @NotBlank
    @Column(unique = true)
    private String userName;

    @Email(message = "Must be a properly formatted email address.")
    @NotEmpty(message = "The field must not be empty.")
    private String email;

    @Size(min=8,message="Min 8 characters in password")
    @NotEmpty(message = "The field must not be empty.")
    private String password;

    private Set<String> roles= new HashSet<>();

}
