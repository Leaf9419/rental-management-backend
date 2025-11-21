package com.eeit205team5.rentalmanagement.security.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    // 之後第三方驗證可能要改一下
    @NotBlank(message = "Email不能為空")
    @Email(message = "Email格式不正確")
    private String email;

    @NotBlank(message = "密碼不能為空")
    @Size(min = 8, message = "密碼至少8個字元")
    @Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d].+$",
        message = "密碼需包含英文字母與數字"
    )
    private String password;

    @NotBlank(message = "請選擇角色")
    private String role; // tenant、landlord

    private String phone;
    
    private String fullName;
}