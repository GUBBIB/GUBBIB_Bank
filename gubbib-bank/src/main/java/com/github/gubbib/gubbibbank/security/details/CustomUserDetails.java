package com.github.gubbib.gubbibbank.security.details;

import com.github.gubbib.gubbibbank.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final Member member;

    @Override
    // 로그인 성공시 security는 계속 권한을 확인하기 위해 호출한다
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority("ROLE_" + member.getRole().name())
        );
    }

    @Override
    // security가 로그인을 검증할 때 호출한다
    public @Nullable String getPassword() {
        return member.getPassword();
    }

    @Override
    // security 로그인시 getUsername을 호출한다
    public String getUsername() {
        return member.getEmail();
    }

    public Member getMember(){
        return member;
    }
}
