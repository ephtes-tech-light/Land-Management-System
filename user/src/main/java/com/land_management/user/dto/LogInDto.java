package com.land_management.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LogInDto {
    @NotBlank
    String username;

    @NotBlank
    String password;
}
