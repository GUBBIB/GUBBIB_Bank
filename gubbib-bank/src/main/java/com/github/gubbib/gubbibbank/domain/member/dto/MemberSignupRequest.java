package com.github.gubbib.gubbibbank.domain.member.dto;

public record MemberSignupRequest(
        String email,
        String password,
        String name,
        String phone
){
}
