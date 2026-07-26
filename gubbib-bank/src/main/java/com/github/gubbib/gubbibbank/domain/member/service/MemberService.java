package com.github.gubbib.gubbibbank.domain.member.service;

import com.github.gubbib.gubbibbank.domain.member.dto.MemberResponse;
import com.github.gubbib.gubbibbank.domain.member.dto.MemberSignupRequest;

public interface MemberService {

    MemberResponse signup(MemberSignupRequest request);
}
