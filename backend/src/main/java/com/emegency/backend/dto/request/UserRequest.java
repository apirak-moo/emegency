package com.emegency.backend.dto.request;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
    
    UUID id,

    @Schema(description = "อีเมลผู้ใช้", example = "apirak@example.com")
    @NotBlank(message = "Email is required")
    @Size(max = 100, message = "Email must be less than 100 characters")
    @Email(message = "Email should be valid")
    String email,

    @Schema(description = "รหัสผ่าน (ต้องมากกว่า 6 ตัวอักษร)", example = "securePassword123")
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    String password

) {

}
