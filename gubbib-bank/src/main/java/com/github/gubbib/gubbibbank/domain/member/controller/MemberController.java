package com.github.gubbib.gubbibbank.domain.member.controller;

import com.github.gubbib.gubbibbank.domain.auth.dto.LoginResponse;
import com.github.gubbib.gubbibbank.domain.auth.dto.SignupRequest;
import com.github.gubbib.gubbibbank.domain.member.dto.MemberResponse;
import com.github.gubbib.gubbibbank.domain.member.service.MemberService;
import com.github.gubbib.gubbibbank.security.details.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor(onConstructor_ =  @Autowired)
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> me(
            @AuthenticationPrincipal CustomUserDetails user
    ){
        return ResponseEntity.ok().body(MemberResponse.from(user.getMember()));
    }
}
