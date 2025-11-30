package com.eeit205team5.rentalmanagement.security.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyEmailRequest {
    @NotBlank(message = "驗證碼不能為空")
    @Size(min = 6, max = 6, message = "驗證碼必須是6位數")
    private String code;
}
