package com.github.gubbib.gubbibbank.domain.auth.dto;

public record SignupRequest(
        String email,
        String password,
        String name,
        String phone
){
}
