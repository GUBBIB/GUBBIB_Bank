package com.github.gubbib.gubbibbank.domain.auth.dto;

import com.github.gubbib.gubbibbank.domain.member.entity.Member;
import com.github.gubbib.gubbibbank.domain.member.entity.MemberStatus;
import com.github.gubbib.gubbibbank.domain.member.entity.Role;
import lombok.Builder;

@Builder
public record LoginResponse(
        String accessToken,
        long expiresIn

) {
    public static LoginResponse from(String accessToken, long expiresIn) {
        return LoginResponse.builder()
                .accessToken(accessToken)
                .expiresIn(expiresIn)
                .build();
    }
}
