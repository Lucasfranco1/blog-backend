package com.blog.demo.auth.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AuthenticationRequestDTO {
    @NotBlank
    private String userName;
    @NotBlank
    @Size(min = 8, max = 20, message = "Debe tener entre 8 y 20 caracteres")
    private String password;
}
