package com.eeit205team5.rentalmanagement.security.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @NotBlank(message = "Email不能為空")
    private String email;
    
    @NotBlank(message = "密碼不能為空")
    private String password;
}