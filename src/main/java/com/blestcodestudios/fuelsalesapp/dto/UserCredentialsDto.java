package com.blestcodestudios.fuelsalesapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCredentialsDto {
    private String username;
    private String email;
    private String password;
    private String rePassword;

    @NotBlank
    private String recaptchaResponse;
}