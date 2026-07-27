package com.github.gubbib.gubbibbank.security.service;

import com.github.gubbib.gubbibbank.domain.member.entity.Member;
import com.github.gubbib.gubbibbank.domain.member.service.MemberService;
import com.github.gubbib.gubbibbank.security.details.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberService memberService;

    @Override
    /*
        CustomUserDetails에서 만든 getUsername 을 호출하는 놈
        저 String username 에는 member.getEmail() 값이 들어온다
    */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            Member m = memberService.findActiveMemberByEmail(username);
            return new CustomUserDetails(m);
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("존재하지 않는 회원입니다. " + e.getMessage());
        }

    }
}
