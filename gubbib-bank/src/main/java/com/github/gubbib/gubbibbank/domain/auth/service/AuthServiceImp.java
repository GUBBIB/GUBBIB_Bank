package com.github.gubbib.gubbibbank.domain.auth.service;

import com.github.gubbib.gubbibbank.domain.auth.dto.LoginRequest;
import com.github.gubbib.gubbibbank.domain.auth.dto.LoginResponse;
import com.github.gubbib.gubbibbank.domain.auth.dto.SignupRequest;
import com.github.gubbib.gubbibbank.domain.member.entity.Member;
import com.github.gubbib.gubbibbank.domain.member.service.MemberService;
import com.github.gubbib.gubbibbank.exception.BusinessException;
import com.github.gubbib.gubbibbank.exception.ErrorCode;
import com.github.gubbib.gubbibbank.security.details.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Transactional(readOnly = true)
@Slf4j
public class AuthServiceImp implements AuthService {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public LoginResponse signup(SignupRequest request) {

        log.debug("회원가입 요청: email = {}", request.email());

        if(memberService.existsMember(request.email())){
            log.debug("이미 존재하는 이메일: {}", request.email());
            throw new BusinessException(ErrorCode.MEMBER_ALREADY_EXISTS);
        }

        String encodedPassword = passwordEncoder.encode(request.password());

        Member m = Member.create(
                request.email(),
                encodedPassword,
                request.name(),
                request.phone()
        );

        memberService.register(m);

        log.debug("회원가입 완료: id={}, email={}",
                m.getId(),
                m.getEmail());

        return LoginResponse.from(m);
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        Member m = user.getMember();

        return LoginResponse.from(m);
    }
}
