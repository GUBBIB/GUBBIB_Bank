package com.github.gubbib.gubbibbank.domain.member.service;

import com.github.gubbib.gubbibbank.domain.member.entity.Member;

import java.util.Optional;

public interface MemberService {

    boolean existsMember(String email);
    Member register(Member member);
    Member findActiveMemberByEmail(String email);
}
