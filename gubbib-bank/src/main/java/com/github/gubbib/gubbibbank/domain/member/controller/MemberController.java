package com.github.gubbib.gubbibbank.domain.member.controller;

import com.github.gubbib.gubbibbank.domain.member.dto.MemberResponse;
import com.github.gubbib.gubbibbank.domain.member.dto.MemberSignupRequest;
import com.github.gubbib.gubbibbank.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor_ =  @Autowired)
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<MemberResponse> signup(
            @Valid @RequestBody MemberSignupRequest request
    ){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(memberService.signup(request));
    }
}
