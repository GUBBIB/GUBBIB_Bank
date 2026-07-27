package com.github.gubbib.gubbibbank.domain.auth.dto;

import com.github.gubbib.gubbibbank.domain.member.entity.Member;
import com.github.gubbib.gubbibbank.domain.member.entity.MemberStatus;
import com.github.gubbib.gubbibbank.domain.member.entity.Role;
import lombok.Builder;

@Builder
public record LoginResponse(
        Long id,
        String email,
        String name,
        String phone,
        Role role,
        MemberStatus status

) {
    public static LoginResponse from(Member m){
        return LoginResponse.builder()
                .id(m.getId())
                .email(m.getEmail())
                .name(m.getName())
                .phone(m.getPhone())
                .role(m.getRole())
                .status(m.getStatus())
                .build();
    }
}
